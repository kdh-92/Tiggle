//package com.side.tiggle.global.scheduler
//
//import com.side.tiggle.domain.scheduler.service.JobService
//import io.kotest.core.spec.style.BehaviorSpec
//import io.kotest.matchers.shouldBe
//import org.springframework.boot.test.context.SpringBootTest
//import java.time.LocalDate
//
//@SpringBootTest
//class JobServiceTest(
//    private val service: JobService,
//)
//    :BehaviorSpec({
//
//        given("주간 날짜 요청") {
//            `when`("7월 1일(월요일)") {
//                val today = LocalDate.of(2024, 7, 1)
//                val (lastMonday, lastSunday) = service.getDate("weekly", today)
//
//                then("이전 주 날짜가 정확해야 한다.") {
//                    lastMonday shouldBe LocalDate.of(2024, 6, 24)
//                    lastSunday shouldBe LocalDate.of(2024, 6, 30)
//                }
//            }
//        }
//})
