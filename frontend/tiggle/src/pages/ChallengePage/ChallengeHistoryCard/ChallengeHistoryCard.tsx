import { useNavigate } from "react-router-dom";

import dayjs from "dayjs";

import { ChallengeRespDto, ChallengeType } from "@/types/gamification";

import { ChallengeHistoryCardStyle } from "./ChallengeHistoryCardStyle";
import { StatusBadge } from "../ActiveChallenge/ActiveChallengeStyle";

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

interface ChallengeHistoryCardProps {
  challenge: ChallengeRespDto;
}

const ChallengeHistoryCard = ({ challenge }: ChallengeHistoryCardProps) => {
  const navigate = useNavigate();
  const { id, type, status, startDate, endDate, targetDays, achievedDays } =
    challenge;

  return (
    <ChallengeHistoryCardStyle
      $status={status}
      onClick={() => navigate(`/challenges/${id}`)}
    >
      <div className="card-left">
        <span className="card-type">{TYPE_LABEL[type] ?? type}</span>
        <span className="card-date">
          {dayjs(startDate).format("MM.DD")} ~ {dayjs(endDate).format("MM.DD")}
        </span>
      </div>

      <div className="card-right">
        <span className="card-days">
          {achievedDays} / {targetDays}일
        </span>
        <StatusBadge $status={status}>
          {STATUS_LABEL[status] ?? status}
        </StatusBadge>
      </div>
    </ChallengeHistoryCardStyle>
  );
};

export default ChallengeHistoryCard;
