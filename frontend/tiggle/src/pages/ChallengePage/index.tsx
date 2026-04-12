import { Link } from "react-router-dom";

import { useQuery } from "@tanstack/react-query";

import { ChallengeApiControllerService } from "@/generated";
import { challengeKeys } from "@/query/queryKeys";
import { ChallengeRespDto, PageRespDto } from "@/types/gamification";
import withAuth from "@/utils/withAuth";

import ActiveChallenge from "./ActiveChallenge/ActiveChallenge";
import ChallengeHistoryCard from "./ChallengeHistoryCard/ChallengeHistoryCard";
import { ChallengePageStyle } from "./ChallengePageStyle";

const ChallengePage = () => {
  const { data: activeData, isLoading: isActiveLoading } = useQuery({
    queryKey: challengeKeys.active(),
    queryFn: () => ChallengeApiControllerService.getActiveChallenge(),
  });

  const { data: historyData, isLoading: isHistoryLoading } = useQuery({
    queryKey: challengeKeys.history(0),
    queryFn: () => ChallengeApiControllerService.getChallengeHistory(0, 10),
  });

  const activeChallenge = activeData?.data
    ? (activeData.data as ChallengeRespDto)
    : null;
  const historyPage = historyData?.data
    ? (historyData.data as PageRespDto<ChallengeRespDto>)
    : null;

  const hasActiveChallenge = !!activeChallenge;

  if (isActiveLoading && isHistoryLoading) {
    return (
      <ChallengePageStyle>
        <div className="loading">불러오는 중...</div>
      </ChallengePageStyle>
    );
  }

  return (
    <ChallengePageStyle>
      <h1 className="page-title">챌린지</h1>

      {activeChallenge && (
        <div className="active-section">
          <ActiveChallenge challenge={activeChallenge} />
        </div>
      )}

      {hasActiveChallenge ? (
        <button className="create-button" disabled>
          진행 중인 챌린지가 있습니다
        </button>
      ) : (
        <Link to="/challenges/create" style={{ textDecoration: "none" }}>
          <button className="create-button">새 챌린지 시작</button>
        </Link>
      )}

      <div className="history-section">
        <h2 className="history-title">히스토리</h2>

        {historyPage && historyPage.content.length > 0 ? (
          <div className="history-list">
            {historyPage.content.map(challenge => (
              <ChallengeHistoryCard key={challenge.id} challenge={challenge} />
            ))}
          </div>
        ) : (
          <div className="empty-history">아직 완료된 챌린지가 없습니다</div>
        )}
      </div>
    </ChallengePageStyle>
  );
};

export default withAuth(ChallengePage);
