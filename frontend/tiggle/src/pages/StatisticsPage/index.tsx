import { useQuery } from "@tanstack/react-query";

import { StatisticsApiControllerService } from "@/generated";
import { statisticsKeys } from "@/query/queryKeys";
import type {
  WeeklyComparisonRespDto,
  MonthlySummaryRespDto,
} from "@/types/gamification";
import withAuth from "@/utils/withAuth";

import CategoryBreakdown from "./CategoryBreakdown/CategoryBreakdown";
import { StatisticsPageWrapper } from "./StatisticsPageStyle";
import WeeklyComparison from "./WeeklyComparison/WeeklyComparison";

const formatAmount = (amount: number): string =>
  `${amount.toLocaleString("ko-KR")}원`;

const StatisticsPage = () => {
  const {
    data: weeklyResp,
    isLoading: isWeeklyLoading,
    isError: isWeeklyError,
  } = useQuery({
    queryKey: statisticsKeys.weekly(0),
    queryFn: () => StatisticsApiControllerService.getWeeklyComparison(0),
  });

  const now = new Date();
  const {
    data: monthlyResp,
    isLoading: isMonthlyLoading,
    isError: isMonthlyError,
  } = useQuery({
    queryKey: statisticsKeys.monthly(now.getFullYear(), now.getMonth() + 1),
    queryFn: () =>
      StatisticsApiControllerService.getMonthlySummary(
        now.getFullYear(),
        now.getMonth() + 1,
      ),
  });

  const isLoading = isWeeklyLoading || isMonthlyLoading;

  const weeklyData = weeklyResp?.data
    ? (weeklyResp.data as WeeklyComparisonRespDto)
    : null;

  const monthlyData = monthlyResp?.data
    ? (monthlyResp.data as MonthlySummaryRespDto)
    : null;

  if (isLoading) {
    return (
      <StatisticsPageWrapper>
        <p className="page-title">소비 통계</p>
        <div className="loading-container">
          <p>불러오는 중...</p>
        </div>
      </StatisticsPageWrapper>
    );
  }

  if (isWeeklyError && isMonthlyError) {
    return (
      <StatisticsPageWrapper>
        <p className="page-title">소비 통계</p>
        <div className="loading-container">
          <p>통계를 불러올 수 없습니다. 잠시 후 다시 시도해 주세요.</p>
        </div>
      </StatisticsPageWrapper>
    );
  }

  return (
    <StatisticsPageWrapper>
      <p className="page-title">소비 통계</p>

      {weeklyData && <WeeklyComparison data={weeklyData} />}

      {monthlyData && (
        <>
          <div className="summary-cards">
            <div className="summary-card outcome">
              <p className="summary-label">이번 달 지출</p>
              <p className="summary-amount">
                {formatAmount(monthlyData.totalOutcome)}
              </p>
            </div>
            <div className="summary-card income">
              <p className="summary-label">이번 달 수입</p>
              <p className="summary-amount">
                {formatAmount(monthlyData.totalIncome)}
              </p>
            </div>
            <div className="summary-card refund">
              <p className="summary-label">환불</p>
              <p className="summary-amount">
                {formatAmount(monthlyData.totalRefund)}
              </p>
            </div>
          </div>

          <div className="section-divider" />

          <CategoryBreakdown data={monthlyData.categoryBreakdown} />
        </>
      )}
    </StatisticsPageWrapper>
  );
};

export default withAuth(StatisticsPage);
