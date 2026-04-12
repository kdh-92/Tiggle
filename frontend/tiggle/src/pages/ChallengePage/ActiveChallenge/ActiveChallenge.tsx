import { Link } from "react-router-dom";

import dayjs from "dayjs";

import { ChallengeRespDto, ChallengeType } from "@/types/gamification";

import { ActiveChallengeStyle, StatusBadge } from "./ActiveChallengeStyle";

const STATUS_LABEL: Record<string, string> = {
  ACTIVE: "진행중",
  COMPLETED: "완료",
  FAILED: "실패",
  CANCELLED: "취소됨",
};

const TYPE_LABEL: Record<string, string> = {
  [ChallengeType.NO_SPEND]: "무소비 챌린지",
  [ChallengeType.BUDGET_LIMIT]: "예산 제한 챌린지",
};

interface ActiveChallengeProps {
  challenge: ChallengeRespDto;
}

const ActiveChallenge = ({ challenge }: ActiveChallengeProps) => {
  const { achievedDays, targetDays, startDate, endDate, status, type, id } =
    challenge;

  const percentage = targetDays > 0 ? (achievedDays / targetDays) * 100 : 0;
  const radius = 34;
  const circumference = 2 * Math.PI * radius;
  const strokeDashoffset = circumference - (percentage / 100) * circumference;

  return (
    <ActiveChallengeStyle>
      <div className="challenge-header">
        <span className="challenge-type">{TYPE_LABEL[type] ?? type}</span>
        <StatusBadge $status={status}>
          {STATUS_LABEL[status] ?? status}
        </StatusBadge>
      </div>

      <div className="progress-section">
        <div className="progress-ring">
          <svg width="80" height="80" viewBox="0 0 80 80">
            <circle className="progress-bg" cx="40" cy="40" r={radius} />
            <circle
              className="progress-fill"
              cx="40"
              cy="40"
              r={radius}
              strokeDasharray={circumference}
              strokeDashoffset={strokeDashoffset}
            />
          </svg>
          <div className="progress-text">{Math.round(percentage)}%</div>
        </div>

        <div className="progress-info">
          <span className="days-text">
            {achievedDays}일 / {targetDays}일
          </span>
          <span className="target-text">
            목표까지 {Math.max(0, targetDays - achievedDays)}일 남음
          </span>
        </div>
      </div>

      <div className="date-range">
        {dayjs(startDate).format("YYYY.MM.DD")} ~{" "}
        {dayjs(endDate).format("YYYY.MM.DD")}
      </div>

      <Link className="detail-link" to={`/challenges/${id}`}>
        상세 보기
      </Link>
    </ActiveChallengeStyle>
  );
};

export default ActiveChallenge;
