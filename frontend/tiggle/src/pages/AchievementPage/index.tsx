import { useQuery } from "@tanstack/react-query";

import { AchievementApiControllerService } from "@/generated";
import { achievementKeys } from "@/query/queryKeys";
import type { AchievementRespDto } from "@/types/gamification";
import withAuth, { AuthProps } from "@/utils/withAuth";

import AchievementCard from "./AchievementCard/AchievementCard";
import {
  AchievementList,
  AchievementPageContainer,
  AllSection,
  EmptyMessage,
  PageTitle,
  RecentSection,
  SectionDivider,
  SectionTitle,
  StatBox,
  StatLabel,
  StatNumber,
  StatsSummary,
} from "./AchievementPageStyle";

interface AchievementPageProps extends AuthProps {}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const AchievementPage = (_props: AchievementPageProps) => {
  // -- Queries --
  const { data: allRes } = useQuery({
    queryKey: achievementKeys.lists(),
    queryFn: () => AchievementApiControllerService.getAchievements(),
  });

  const { data: recentRes } = useQuery({
    queryKey: achievementKeys.recent(3),
    queryFn: () => AchievementApiControllerService.getRecentAchievements(3),
  });

  const allAchievements: AchievementRespDto[] = allRes?.data ?? [];
  const recentAchievements: AchievementRespDto[] = recentRes?.data ?? [];

  const achievedCount = allAchievements.filter(a => a.achieved).length;
  const totalCount = allAchievements.length;

  return (
    <AchievementPageContainer>
      <PageTitle>업적</PageTitle>

      {/* Summary stats */}
      <StatsSummary>
        <StatBox>
          <StatNumber>{achievedCount}</StatNumber>
          <StatLabel>달성 완료</StatLabel>
        </StatBox>
        <StatBox>
          <StatNumber>{totalCount - achievedCount}</StatNumber>
          <StatLabel>미달성</StatLabel>
        </StatBox>
        <StatBox>
          <StatNumber>
            {totalCount > 0
              ? Math.round((achievedCount / totalCount) * 100)
              : 0}
            %
          </StatNumber>
          <StatLabel>달성률</StatLabel>
        </StatBox>
      </StatsSummary>

      {/* Recent achievements */}
      {recentAchievements.length > 0 && (
        <RecentSection>
          <SectionTitle>최근 달성 업적</SectionTitle>
          <AchievementList>
            {recentAchievements.map(achievement => (
              <AchievementCard key={achievement.id} achievement={achievement} />
            ))}
          </AchievementList>
        </RecentSection>
      )}

      <SectionDivider />

      {/* All achievements */}
      <AllSection>
        <SectionTitle>전체 업적</SectionTitle>
        {allAchievements.length === 0 ? (
          <EmptyMessage>업적 목록이 비어있습니다.</EmptyMessage>
        ) : (
          <AchievementList>
            {allAchievements.map(achievement => (
              <AchievementCard key={achievement.id} achievement={achievement} />
            ))}
          </AchievementList>
        )}
      </AllSection>
    </AchievementPageContainer>
  );
};

export default withAuth(AchievementPage);
