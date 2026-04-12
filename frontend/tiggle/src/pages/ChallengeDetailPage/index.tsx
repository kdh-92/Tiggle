import { useNavigate, useParams } from "react-router-dom";

import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import dayjs from "dayjs";

import { ChallengeApiControllerService } from "@/generated";
import useMessage from "@/hooks/useMessage";
import { challengeKeys } from "@/query/queryKeys";
import {
  ChallengeDetailRespDto,
  ChallengeStatus,
  ChallengeType,
} from "@/types/gamification";
import withAuth from "@/utils/withAuth";

import { ChallengeDetailPageStyle } from "./ChallengeDetailPageStyle";
import DailyLogCalendar from "./DailyLogCalendar/DailyLogCalendar";
import { StatusBadge } from "../ChallengePage/ActiveChallenge/ActiveChallengeStyle";

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

const ChallengeDetailPage = () => {
  const { id } = useParams();
  const challengeId = Number(id);
  const navigate = useNavigate();
  const messageApi = useMessage();
  const queryClient = useQueryClient();

  const { data, isLoading } = useQuery({
    queryKey: challengeKeys.detail(challengeId),
    queryFn: () =>
      ChallengeApiControllerService.getChallengeDetail(challengeId),
    enabled: !isNaN(challengeId),
  });

  const { mutate: cancelMutate, isLoading: isCancelling } = useMutation({
    mutationFn: () =>
      ChallengeApiControllerService.cancelChallenge(challengeId),
    onSuccess: () => {
      messageApi.open({
        type: "success",
        content: "챌린지가 취소되었습니다.",
      });
      queryClient.invalidateQueries({ queryKey: challengeKeys.active() });
      queryClient.invalidateQueries({ queryKey: challengeKeys.history(0) });
      queryClient.invalidateQueries({
        queryKey: challengeKeys.detail(challengeId),
      });
      navigate("/challenges");
    },
    onError: () => {
      messageApi.open({
        type: "error",
        content: "챌린지 취소에 실패했습니다.",
      });
    },
  });

  const handleCancel = () => {
    if (window.confirm("정말로 이 챌린지를 취소하시겠습니까?")) {
      cancelMutate();
    }
  };

  if (isLoading) {
    return (
      <ChallengeDetailPageStyle>
        <div className="loading">불러오는 중...</div>
      </ChallengeDetailPageStyle>
    );
  }

  const detail = data?.data ? (data.data as ChallengeDetailRespDto) : null;

  if (!detail) {
    return (
      <ChallengeDetailPageStyle>
        <div className="loading">챌린지를 찾을 수 없습니다.</div>
      </ChallengeDetailPageStyle>
    );
  }

  const { challenge, dailyLogs } = detail;
  const isActive = challenge.status === ChallengeStatus.ACTIVE;

  return (
    <ChallengeDetailPageStyle>
      <h1 className="page-title">챌린지 상세</h1>

      <div className="challenge-info">
        <div className="info-header">
          <span className="info-type">
            {TYPE_LABEL[challenge.type] ?? challenge.type}
          </span>
          <StatusBadge $status={challenge.status}>
            {STATUS_LABEL[challenge.status] ?? challenge.status}
          </StatusBadge>
        </div>

        <div className="info-stats">
          <div className="stat-item">
            <span className="stat-value">{challenge.achievedDays}</span>
            <span className="stat-label">달성일</span>
          </div>
          <div className="stat-item">
            <span className="stat-value">{challenge.targetDays}</span>
            <span className="stat-label">목표일</span>
          </div>
          <div className="stat-item">
            <span className="stat-value">
              {challenge.targetDays > 0
                ? Math.round(
                    (challenge.achievedDays / challenge.targetDays) * 100,
                  )
                : 0}
              %
            </span>
            <span className="stat-label">달성률</span>
          </div>
        </div>

        <div className="info-date">
          {dayjs(challenge.startDate).format("YYYY.MM.DD")} ~{" "}
          {dayjs(challenge.endDate).format("YYYY.MM.DD")}
        </div>
      </div>

      <div className="calendar-section">
        <h2 className="calendar-title">일별 기록</h2>
        <DailyLogCalendar
          logs={dailyLogs}
          startDate={challenge.startDate}
          endDate={challenge.endDate}
        />
      </div>

      {isActive && (
        <button
          className="cancel-button"
          onClick={handleCancel}
          disabled={isCancelling}
        >
          {isCancelling ? "취소 중..." : "챌린지 취소"}
        </button>
      )}

      <button className="back-button" onClick={() => navigate("/challenges")}>
        목록으로
      </button>
    </ChallengeDetailPageStyle>
  );
};

export default withAuth(ChallengeDetailPage);
