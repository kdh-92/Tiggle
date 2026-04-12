import {
  ExpBarWrapper,
  ExpBarHeader,
  LevelBadge,
  ExpText,
  BarTrack,
  BarFill,
} from "./ExpProgressBarStyle";

interface ExpProgressBarProps {
  experience: number;
  nextLevelExp: number | null;
  level: number;
}

const ExpProgressBar = ({
  experience,
  nextLevelExp,
  level,
}: ExpProgressBarProps) => {
  const isMaxLevel = nextLevelExp === null;
  const percent = isMaxLevel
    ? 100
    : nextLevelExp > 0
      ? Math.min(Math.round((experience / nextLevelExp) * 100), 100)
      : 0;

  return (
    <ExpBarWrapper>
      <ExpBarHeader>
        <LevelBadge>Lv. {level}</LevelBadge>
        <ExpText>
          {isMaxLevel
            ? "MAX"
            : `${experience.toLocaleString()} / ${nextLevelExp.toLocaleString()}`}
        </ExpText>
      </ExpBarHeader>
      <BarTrack>
        <BarFill $percent={percent} />
      </BarTrack>
    </ExpBarWrapper>
  );
};

export default ExpProgressBar;
