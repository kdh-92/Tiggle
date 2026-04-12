import { ArrowDown, ArrowUp, AlertTriangle } from "react-feather";

import type { WeeklyComparisonRespDto } from "@/types/gamification";

import { WeeklyComparisonWrapper } from "./WeeklyComparisonStyle";

interface WeeklyComparisonProps {
  data: WeeklyComparisonRespDto;
}

const formatAmount = (amount: number): string =>
  `${amount.toLocaleString("ko-KR")}원`;

const formatPeriod = (start: string, end: string): string =>
  `${start.slice(5)} ~ ${end.slice(5)}`;

const WeeklyComparison = ({ data }: WeeklyComparisonProps) => {
  const { currentWeek, previousWeek, changeRate, isImproved, isAnomaly } = data;

  const getChangeClass = (): string => {
    if (isImproved === null) return "neutral";
    return isImproved ? "improved" : "worsened";
  };

  const getChangeLabel = (): string => {
    if (changeRate === null || isImproved === null) return "비교 데이터 없음";
    const absRate = Math.abs(changeRate).toFixed(1);
    if (isImproved) {
      return `지난주 대비 ${absRate}% 절약`;
    }
    return `지난주 대비 ${absRate}% 증가`;
  };

  return (
    <WeeklyComparisonWrapper>
      <p className="section-title">주간 소비 비교</p>

      <div className="comparison-row">
        <div className="week-card">
          <p className="week-label">이번 주</p>
          <p className="week-amount">
            {formatAmount(currentWeek.totalOutcome)}
          </p>
          <p className="week-period">
            {formatPeriod(currentWeek.weekStartDate, currentWeek.weekEndDate)}
          </p>
        </div>

        {previousWeek && (
          <div className="week-card">
            <p className="week-label">지난 주</p>
            <p className="week-amount">
              {formatAmount(previousWeek.totalOutcome)}
            </p>
            <p className="week-period">
              {formatPeriod(
                previousWeek.weekStartDate,
                previousWeek.weekEndDate,
              )}
            </p>
          </div>
        )}
      </div>

      {previousWeek ? (
        <div className={`change-indicator ${getChangeClass()}`}>
          {isImproved !== null &&
            (isImproved ? (
              <ArrowDown className="arrow-icon" size={16} />
            ) : (
              <ArrowUp className="arrow-icon" size={16} />
            ))}
          <span className="change-text">{getChangeLabel()}</span>
        </div>
      ) : (
        <p className="no-previous">
          첫 주 사용 중이에요. 다음 주부터 비교 데이터가 제공됩니다.
        </p>
      )}

      {isAnomaly && (
        <div className="anomaly-badge">
          <AlertTriangle size={14} />
          <span>이상 소비 감지</span>
        </div>
      )}
    </WeeklyComparisonWrapper>
  );
};

export default WeeklyComparison;
