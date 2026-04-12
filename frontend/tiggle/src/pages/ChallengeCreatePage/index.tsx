import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { useMutation, useQueryClient } from "@tanstack/react-query";
import dayjs from "dayjs";

import { ChallengeApiControllerService } from "@/generated";
import useMessage from "@/hooks/useMessage";
import { challengeKeys } from "@/query/queryKeys";
import { ChallengeType } from "@/types/gamification";
import withAuth from "@/utils/withAuth";

import { ChallengeCreatePageStyle } from "./ChallengeCreatePageStyle";

const TYPE_LABEL: Record<string, string> = {
  [ChallengeType.NO_SPEND]: "무소비 챌린지",
};

const ChallengeCreatePage = () => {
  const navigate = useNavigate();
  const messageApi = useMessage();
  const queryClient = useQueryClient();

  const [type] = useState<string>(ChallengeType.NO_SPEND);
  const [targetDays, setTargetDays] = useState<number>(7);

  const startDate = dayjs();
  const endDate = startDate.add(targetDays, "day");

  const { mutate, isLoading } = useMutation({
    mutationFn: () =>
      ChallengeApiControllerService.createChallenge({
        type,
        targetDays,
      }),
    onSuccess: () => {
      messageApi.open({
        type: "success",
        content: "챌린지가 시작되었습니다!",
      });
      queryClient.invalidateQueries({ queryKey: challengeKeys.active() });
      queryClient.invalidateQueries({ queryKey: challengeKeys.history(0) });
      navigate("/challenges");
    },
    onError: () => {
      messageApi.open({
        type: "error",
        content: "챌린지 생성에 실패했습니다.",
      });
    },
  });

  const handleTargetDaysChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = parseInt(e.target.value, 10);
    if (isNaN(value)) return;
    setTargetDays(Math.min(30, Math.max(1, value)));
  };

  const handleSubmit = () => {
    if (targetDays < 1 || targetDays > 30) return;
    mutate();
  };

  return (
    <ChallengeCreatePageStyle>
      <h1 className="page-title">새 챌린지</h1>

      <div className="form">
        <div className="field">
          <label className="field-label">챌린지 종류</label>
          <select className="field-select" value={type} disabled>
            {Object.entries(TYPE_LABEL).map(([key, label]) => (
              <option key={key} value={key}>
                {label}
              </option>
            ))}
          </select>
        </div>

        <div className="field">
          <label className="field-label">목표 일수 (1~30일)</label>
          <input
            className="field-input"
            type="number"
            min={1}
            max={30}
            value={targetDays}
            onChange={handleTargetDaysChange}
          />
        </div>

        <div className="date-preview">
          <span className="date-label">시작일</span>
          <span className="date-value">
            {startDate.format("YYYY.MM.DD (ddd)")}
          </span>
        </div>

        <div className="date-preview">
          <span className="date-label">종료일</span>
          <span className="date-value">
            {endDate.format("YYYY.MM.DD (ddd)")}
          </span>
        </div>

        <button
          className="submit-button"
          onClick={handleSubmit}
          disabled={isLoading || targetDays < 1 || targetDays > 30}
        >
          {isLoading ? "생성 중..." : "챌린지 시작하기"}
        </button>

        <button
          className="cancel-button"
          onClick={() => navigate("/challenges")}
        >
          취소
        </button>
      </div>
    </ChallengeCreatePageStyle>
  );
};

export default withAuth(ChallengeCreatePage);
