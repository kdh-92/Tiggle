import dayjs from "dayjs";

import type { AchievementRespDto } from "@/types/gamification";

import {
  AchievedDate,
  AchievementCondition,
  AchievementDescription,
  AchievementIcon,
  AchievementName,
  CardBody,
  CardContainer,
} from "./AchievementCardStyle";

const CONDITION_LABELS: Record<string, string> = {
  RECORD_COUNT: "거래 기록",
  STREAK: "연속 기록",
  CHALLENGE_COMPLETE: "챌린지 완료",
  CATEGORY_COUNT: "카테고리 사용",
  SPENDING_DECREASE: "지출 감소",
  NO_ANOMALY_WEEKS: "정상 소비 주간",
  NO_SPEND_DAYS: "무지출일",
  COLOR_RARITY: "컬러 희귀도",
  CHARACTER_TIER: "캐릭터 등급",
};

interface AchievementCardProps {
  achievement: AchievementRespDto;
}

const AchievementCard = ({ achievement }: AchievementCardProps) => {
  const {
    name,
    description,
    conditionType,
    conditionValue,
    achieved,
    achievedAt,
  } = achievement;

  const conditionLabel = CONDITION_LABELS[conditionType] ?? conditionType;

  return (
    <CardContainer $achieved={achieved}>
      <AchievementIcon $achieved={achieved}>
        {achieved ? "\u2705" : "\uD83D\uDD12"}
      </AchievementIcon>
      <CardBody>
        <AchievementName>{name}</AchievementName>
        {description && (
          <AchievementDescription>{description}</AchievementDescription>
        )}
        <AchievementCondition>
          {conditionLabel} {conditionValue}
          {conditionType === "RECORD_COUNT" && "회"}
          {conditionType === "STREAK" && "일 연속"}
          {conditionType === "CHALLENGE_COMPLETE" && "회"}
          {conditionType === "CATEGORY_COUNT" && "개"}
          {conditionType === "SPENDING_DECREASE" && "%"}
          {conditionType === "NO_ANOMALY_WEEKS" && "주"}
          {conditionType === "NO_SPEND_DAYS" && "일"}
        </AchievementCondition>
        {achieved && achievedAt && (
          <AchievedDate>
            {"\u2705"} {dayjs(achievedAt).format("YYYY.MM.DD")} 달성
          </AchievedDate>
        )}
      </CardBody>
    </CardContainer>
  );
};

export default AchievementCard;
