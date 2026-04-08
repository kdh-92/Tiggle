package com.side.tiggle.domain.statistics.service

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.repository.CategoryRepository
import com.side.tiggle.domain.statistics.model.WeeklyStatSnapshot
import com.side.tiggle.domain.statistics.repository.WeeklyStatSnapshotRepository
import com.side.tiggle.domain.transaction.repository.TransactionRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*
import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.*

class StatisticsServiceImplTest : StringSpec({

    val weeklyStatSnapshotRepository: WeeklyStatSnapshotRepository = mockk()
    val transactionRepository: TransactionRepository = mockk()
    val categoryRepository: CategoryRepository = mockk()

    val statisticsService: StatisticsService = StatisticsServiceImpl(
        weeklyStatSnapshotRepository,
        transactionRepository,
        categoryRepository
    )

    beforeEach {
        clearAllMocks()
    }

    fun mondayOfCurrentWeek(): LocalDate =
        LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    fun createSnapshot(
        memberId: Long = 1L,
        weekStartDate: LocalDate,
        totalOutcome: Int = 50000,
        totalIncome: Int = 100000,
        totalRefund: Int = 0,
        transactionCount: Int = 5,
        avgDailyOutcome: Int = totalOutcome / 7,
        topCategoryId: Long? = 1L,
        isAnomaly: Boolean = false,
        anomalyRatio: Double? = null
    ): WeeklyStatSnapshot {
        return WeeklyStatSnapshot(
            memberId = memberId,
            weekStartDate = weekStartDate,
            weekEndDate = weekStartDate.plusDays(6),
            totalOutcome = totalOutcome,
            totalIncome = totalIncome,
            totalRefund = totalRefund,
            transactionCount = transactionCount,
            avgDailyOutcome = avgDailyOutcome,
            topCategoryId = topCategoryId,
            isAnomaly = isAnomaly,
            anomalyRatio = anomalyRatio
        ).apply { id = 1L }
    }

    "주간 비교 - 현재 주와 이전 주가 모두 존재하면 비교 결과를 반환합니다" {
        // given
        val memberId = 1L
        val currentWeekStart = mondayOfCurrentWeek()
        val previousWeekStart = currentWeekStart.minusWeeks(1)

        val currentSnapshot = createSnapshot(
            weekStartDate = currentWeekStart,
            totalOutcome = 60000,
            topCategoryId = 1L
        )
        val previousSnapshot = createSnapshot(
            weekStartDate = previousWeekStart,
            totalOutcome = 50000,
            topCategoryId = 2L
        )

        val category1 = Category("식비", true, 0L).apply { id = 1L }
        val category2 = Category("교통비", true, 0L).apply { id = 2L }

        every { weeklyStatSnapshotRepository.findByMemberIdAndWeekStartDate(memberId, currentWeekStart) } returns currentSnapshot
        every { weeklyStatSnapshotRepository.findByMemberIdAndWeekStartDate(memberId, previousWeekStart) } returns previousSnapshot
        every { categoryRepository.findById(1L) } returns Optional.of(category1)
        every { categoryRepository.findById(2L) } returns Optional.of(category2)

        // when
        val result = statisticsService.getWeeklyComparison(memberId, 0)

        // then
        result.currentWeek.totalOutcome shouldBe 60000
        result.previousWeek shouldNotBe null
        result.previousWeek!!.totalOutcome shouldBe 50000
        result.changeRate shouldNotBe null
        result.changeRate!! shouldBe 20.0 // (60000-50000)/50000 * 100
        result.isImproved shouldBe false // spending increased
    }

    "주간 비교 - 이전 주 데이터가 없으면 생성하여 반환합니다" {
        // given
        val memberId = 1L
        val currentWeekStart = mondayOfCurrentWeek()
        val previousWeekStart = currentWeekStart.minusWeeks(1)
        val previousWeekEnd = previousWeekStart.plusDays(6)

        val currentSnapshot = createSnapshot(weekStartDate = currentWeekStart, totalOutcome = 30000)
        val category1 = Category("식비", true, 0L).apply { id = 1L }

        // Current week exists
        every { weeklyStatSnapshotRepository.findByMemberIdAndWeekStartDate(memberId, currentWeekStart) } returns currentSnapshot

        // Previous week doesn't exist, needs generation
        every { weeklyStatSnapshotRepository.findByMemberIdAndWeekStartDate(memberId, previousWeekStart) } returns null

        // Transaction aggregation for previous week
        every { transactionRepository.sumByMemberIdAndDateRange(memberId, previousWeekStart, previousWeekEnd) } returns emptyList()
        every { transactionRepository.findTopCategoryByOutcome(memberId, previousWeekStart, previousWeekEnd) } returns emptyList()
        every { weeklyStatSnapshotRepository.findTop8ByMemberIdOrderByWeekStartDateDesc(memberId) } returns emptyList()

        val savedSnapshot = createSnapshot(
            weekStartDate = previousWeekStart,
            totalOutcome = 0,
            totalIncome = 0,
            transactionCount = 0,
            avgDailyOutcome = 0,
            topCategoryId = null,
            isAnomaly = false
        )
        every { weeklyStatSnapshotRepository.save(any()) } returns savedSnapshot
        every { categoryRepository.findById(1L) } returns Optional.of(category1)

        // when
        val result = statisticsService.getWeeklyComparison(memberId, 0)

        // then
        result.currentWeek.totalOutcome shouldBe 30000
        result.previousWeek shouldNotBe null
        result.previousWeek!!.totalOutcome shouldBe 0
        result.changeRate shouldBe null // previous totalOutcome is 0
    }

    "이상 감지 - 지출이 최근 8주 평균의 1.5배를 초과하면 이상으로 판정합니다" {
        // given
        val memberId = 1L
        val weekStartDate = mondayOfCurrentWeek()
        val weekEndDate = weekStartDate.plusDays(6)

        // No existing snapshot
        every { weeklyStatSnapshotRepository.findByMemberIdAndWeekStartDate(memberId, weekStartDate) } returns null

        // Transaction data: high spending this week
        val txSums = listOf(
            arrayOf<Any>("OUTCOME", BigDecimal(200000), BigDecimal(10))
        )
        every { transactionRepository.sumByMemberIdAndDateRange(memberId, weekStartDate, weekEndDate) } returns txSums
        every { transactionRepository.findTopCategoryByOutcome(memberId, weekStartDate, weekEndDate) } returns listOf(
            arrayOf<Any>(BigDecimal(1), BigDecimal(200000))
        )

        // Historical snapshots: avg 50000 outcome, so 200000 > 50000 * 1.5
        val historicalSnapshots = (1..4).map { i ->
            createSnapshot(
                weekStartDate = weekStartDate.minusWeeks(i.toLong()),
                totalOutcome = 50000
            )
        }
        every { weeklyStatSnapshotRepository.findTop8ByMemberIdOrderByWeekStartDateDesc(memberId) } returns historicalSnapshots

        val capturedSnapshot = slot<WeeklyStatSnapshot>()
        every { weeklyStatSnapshotRepository.save(capture(capturedSnapshot)) } answers { capturedSnapshot.captured.apply { id = 99L } }

        // when
        val snapshot = statisticsService.generateWeeklySnapshot(memberId, weekStartDate)

        // then
        snapshot.isAnomaly shouldBe true
        snapshot.anomalyRatio shouldNotBe null
        snapshot.anomalyRatio!! shouldBe 4.0 // 200000 / 50000
        snapshot.totalOutcome shouldBe 200000
    }

    "정상 지출 - 지출이 최근 8주 평균의 1.5배 이하이면 정상으로 판정합니다" {
        // given
        val memberId = 1L
        val weekStartDate = mondayOfCurrentWeek()
        val weekEndDate = weekStartDate.plusDays(6)

        every { weeklyStatSnapshotRepository.findByMemberIdAndWeekStartDate(memberId, weekStartDate) } returns null

        val txSums = listOf(
            arrayOf<Any>("OUTCOME", BigDecimal(60000), BigDecimal(5))
        )
        every { transactionRepository.sumByMemberIdAndDateRange(memberId, weekStartDate, weekEndDate) } returns txSums
        every { transactionRepository.findTopCategoryByOutcome(memberId, weekStartDate, weekEndDate) } returns listOf(
            arrayOf<Any>(BigDecimal(1), BigDecimal(60000))
        )

        // Historical snapshots: avg 50000 outcome, 60000 < 50000 * 1.5 = 75000
        val historicalSnapshots = (1..4).map { i ->
            createSnapshot(
                weekStartDate = weekStartDate.minusWeeks(i.toLong()),
                totalOutcome = 50000
            )
        }
        every { weeklyStatSnapshotRepository.findTop8ByMemberIdOrderByWeekStartDateDesc(memberId) } returns historicalSnapshots

        val capturedSnapshot = slot<WeeklyStatSnapshot>()
        every { weeklyStatSnapshotRepository.save(capture(capturedSnapshot)) } answers { capturedSnapshot.captured.apply { id = 99L } }

        // when
        val snapshot = statisticsService.generateWeeklySnapshot(memberId, weekStartDate)

        // then
        snapshot.isAnomaly shouldBe false
        snapshot.anomalyRatio shouldBe null
        snapshot.totalOutcome shouldBe 60000
    }

    "월간 요약 - 이번 달의 지출/수입/환불 및 카테고리별 내역을 반환합니다" {
        // given
        val memberId = 1L
        val today = LocalDate.now()
        val monthStart = today.withDayOfMonth(1)
        val monthEnd = today.with(java.time.temporal.TemporalAdjusters.lastDayOfMonth())

        val txSums = listOf(
            arrayOf<Any>("OUTCOME", BigDecimal(150000), BigDecimal(15)),
            arrayOf<Any>("INCOME", BigDecimal(3000000), BigDecimal(1)),
            arrayOf<Any>("REFUND", BigDecimal(10000), BigDecimal(2))
        )
        every { transactionRepository.sumByMemberIdAndDateRange(memberId, monthStart, monthEnd) } returns txSums

        val categoryBreakdown = listOf(
            arrayOf<Any>(BigDecimal(1), BigDecimal(80000)),
            arrayOf<Any>(BigDecimal(2), BigDecimal(50000)),
            arrayOf<Any>(BigDecimal(3), BigDecimal(20000))
        )
        every { transactionRepository.findCategoryBreakdownByOutcome(memberId, monthStart, monthEnd) } returns categoryBreakdown

        val category1 = Category("식비", true, 0L).apply { id = 1L }
        val category2 = Category("교통비", true, 0L).apply { id = 2L }
        val category3 = Category("쇼핑", true, 0L).apply { id = 3L }

        every { categoryRepository.findById(1L) } returns Optional.of(category1)
        every { categoryRepository.findById(2L) } returns Optional.of(category2)
        every { categoryRepository.findById(3L) } returns Optional.of(category3)

        // when
        val result = statisticsService.getMonthlySummary(memberId)

        // then
        result.year shouldBe today.year
        result.month shouldBe today.monthValue
        result.totalOutcome shouldBe 150000
        result.totalIncome shouldBe 3000000
        result.totalRefund shouldBe 10000
        result.transactionCount shouldBe 18
        result.categoryBreakdown.size shouldBe 3
        result.categoryBreakdown[0].categoryName shouldBe "식비"
        result.categoryBreakdown[0].amount shouldBe 80000
    }

    "빈 데이터 처리 - 거래가 없는 경우 기본값으로 스냅샷을 생성합니다" {
        // given
        val memberId = 1L
        val weekStartDate = mondayOfCurrentWeek()
        val weekEndDate = weekStartDate.plusDays(6)

        every { weeklyStatSnapshotRepository.findByMemberIdAndWeekStartDate(memberId, weekStartDate) } returns null
        every { transactionRepository.sumByMemberIdAndDateRange(memberId, weekStartDate, weekEndDate) } returns emptyList()
        every { transactionRepository.findTopCategoryByOutcome(memberId, weekStartDate, weekEndDate) } returns emptyList()
        every { weeklyStatSnapshotRepository.findTop8ByMemberIdOrderByWeekStartDateDesc(memberId) } returns emptyList()

        val capturedSnapshot = slot<WeeklyStatSnapshot>()
        every { weeklyStatSnapshotRepository.save(capture(capturedSnapshot)) } answers { capturedSnapshot.captured.apply { id = 99L } }

        // when
        val snapshot = statisticsService.generateWeeklySnapshot(memberId, weekStartDate)

        // then
        snapshot.totalOutcome shouldBe 0
        snapshot.totalIncome shouldBe 0
        snapshot.totalRefund shouldBe 0
        snapshot.transactionCount shouldBe 0
        snapshot.avgDailyOutcome shouldBe 0
        snapshot.topCategoryId shouldBe null
        snapshot.isAnomaly shouldBe false
        snapshot.anomalyRatio shouldBe null
    }

    "빈 데이터 처리 - 거래가 없는 달의 월간 요약은 기본값을 반환합니다" {
        // given
        val memberId = 1L
        val today = LocalDate.now()
        val monthStart = today.withDayOfMonth(1)
        val monthEnd = today.with(java.time.temporal.TemporalAdjusters.lastDayOfMonth())

        every { transactionRepository.sumByMemberIdAndDateRange(memberId, monthStart, monthEnd) } returns emptyList()
        every { transactionRepository.findCategoryBreakdownByOutcome(memberId, monthStart, monthEnd) } returns emptyList()

        // when
        val result = statisticsService.getMonthlySummary(memberId)

        // then
        result.totalOutcome shouldBe 0
        result.totalIncome shouldBe 0
        result.totalRefund shouldBe 0
        result.transactionCount shouldBe 0
        result.categoryBreakdown shouldBe emptyList()
    }

    "스냅샷 생성 - 이미 존재하는 스냅샷이 있으면 재생성하지 않고 반환합니다" {
        // given
        val memberId = 1L
        val weekStartDate = mondayOfCurrentWeek()
        val existingSnapshot = createSnapshot(weekStartDate = weekStartDate, totalOutcome = 70000)

        every { weeklyStatSnapshotRepository.findByMemberIdAndWeekStartDate(memberId, weekStartDate) } returns existingSnapshot

        // when
        val result = statisticsService.generateWeeklySnapshot(memberId, weekStartDate)

        // then
        result shouldBe existingSnapshot
        verify(exactly = 0) { transactionRepository.sumByMemberIdAndDateRange(any(), any(), any()) }
        verify(exactly = 0) { weeklyStatSnapshotRepository.save(any()) }
    }
})
