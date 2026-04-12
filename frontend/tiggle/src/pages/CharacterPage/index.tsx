import { ChevronRight } from "react-feather";

import { useQuery } from "@tanstack/react-query";

import { CharacterApiControllerService } from "@/generated/services/CharacterApiControllerService";
import { characterKeys } from "@/query/queryKeys";
import {
  type CharacterDetailRespDto,
  CharacterStage,
} from "@/types/gamification";
import withAuth, { type AuthProps } from "@/utils/withAuth";

import CharacterDisplay from "./CharacterDisplay/CharacterDisplay";
import {
  CharacterPageStyle,
  PageTitle,
  CharacterSection,
  InfoCard,
  InfoRow,
  InfoLabel,
  InfoValue,
  CatalogLink,
  EmptyState,
} from "./CharacterPageStyle";
import ExpProgressBar from "./ExpProgressBar/ExpProgressBar";

const PATH_LABELS: Record<string, string> = {
  GOLD: "재물의 길",
  NATURE: "자연의 길",
  OCEAN: "바다의 길",
  STAR: "별의 길",
  DRAGON: "용의 길",
};

interface CharacterPageProps extends AuthProps {}

const CharacterPage = ({ profile }: CharacterPageProps) => {
  const { data, isLoading, isError } = useQuery({
    queryKey: characterKeys.me(),
    queryFn: () => CharacterApiControllerService.getMyCharacter(),
    staleTime: 1000 * 60 * 5,
  });

  const character = data?.data as CharacterDetailRespDto | undefined;

  if (isLoading) {
    return (
      <CharacterPageStyle>
        <EmptyState>불러오는 중...</EmptyState>
      </CharacterPageStyle>
    );
  }

  if (isError || !character) {
    return (
      <CharacterPageStyle>
        <EmptyState>캐릭터 정보를 불러올 수 없습니다.</EmptyState>
      </CharacterPageStyle>
    );
  }

  const isEgg = character.stage === CharacterStage.EGG;

  return (
    <CharacterPageStyle>
      <PageTitle>{profile.nickname}의 캐릭터</PageTitle>

      <CharacterSection>
        <CharacterDisplay character={character} />

        <ExpProgressBar
          experience={character.experience}
          nextLevelExp={character.nextLevelExp}
          level={character.level}
        />

        {!isEgg && character.currentForm && (
          <InfoCard>
            <InfoRow>
              <InfoLabel>형태</InfoLabel>
              <InfoValue>{character.currentForm.name}</InfoValue>
            </InfoRow>
            {character.tier && (
              <InfoRow>
                <InfoLabel>등급</InfoLabel>
                <InfoValue>
                  {
                    {
                      COMMON: "일반",
                      RARE: "희귀",
                      EPIC: "영웅",
                      LEGENDARY: "전설",
                      UNIQUE: "유일",
                    }[character.tier]
                  }
                </InfoValue>
              </InfoRow>
            )}
            {character.path && (
              <InfoRow>
                <InfoLabel>성장 경로</InfoLabel>
                <InfoValue>
                  {PATH_LABELS[character.path] ?? character.path}
                </InfoValue>
              </InfoRow>
            )}
            <InfoRow>
              <InfoLabel>레벨</InfoLabel>
              <InfoValue>Lv. {character.level}</InfoValue>
            </InfoRow>
          </InfoCard>
        )}

        {isEgg && character.egg && (
          <InfoCard>
            <InfoRow>
              <InfoLabel>기록 진행</InfoLabel>
              <InfoValue>{character.egg.records} / 30</InfoValue>
            </InfoRow>
            <InfoRow>
              <InfoLabel>현재 단계</InfoLabel>
              <InfoValue>
                {{
                  0: "알",
                  1: "흔들리는 알",
                  2: "금 간 알",
                  3: "부화 직전",
                }[character.egg.phase] ?? "알"}
              </InfoValue>
            </InfoRow>
          </InfoCard>
        )}
      </CharacterSection>

      <CatalogLink to="/mypage/character/catalog">
        캐릭터 도감 보기 <ChevronRight size={18} />
      </CatalogLink>
    </CharacterPageStyle>
  );
};

export default withAuth(CharacterPage);
