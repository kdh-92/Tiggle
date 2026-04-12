import {
  type CharacterDetailRespDto,
  type CharacterTierType,
  type ColorRarityType,
  CharacterStage,
} from "@/types/gamification";

import {
  CharacterDisplayWrapper,
  CharacterOrb,
  EggPhaseText,
  EggProgressText,
  FormName,
  TierBadge,
  RarityBadge,
  CharacterInfoRow,
} from "./CharacterDisplayStyle";

const TIER_COLORS: Record<CharacterTierType, string> = {
  COMMON: "#999999",
  RARE: "#438bea",
  EPIC: "#9333ea",
  LEGENDARY: "#eab308",
  UNIQUE: "#ec4899",
};

const TIER_LABELS: Record<CharacterTierType, string> = {
  COMMON: "일반",
  RARE: "희귀",
  EPIC: "영웅",
  LEGENDARY: "전설",
  UNIQUE: "유일",
};

const EGG_PHASE_LABELS: Record<number, string> = {
  0: "알",
  1: "흔들리는 알",
  2: "금 간 알",
  3: "부화 직전",
};

const RARITY_LABELS: Record<ColorRarityType, string> = {
  NORMAL: "일반",
  SHINE: "빛나는",
  HOLOGRAPHIC: "홀로그램",
  LEGENDARY: "전설",
};

interface CharacterDisplayProps {
  character: CharacterDetailRespDto;
}

const CharacterDisplay = ({ character }: CharacterDisplayProps) => {
  const { stage, tier, color, egg, currentForm } = character;
  const isEgg = stage === CharacterStage.EGG;

  return (
    <CharacterDisplayWrapper>
      <CharacterOrb
        $hue={color.hue}
        $saturation={color.saturation}
        $lightness={color.lightness}
        $rarity={color.rarity}
        $isEgg={isEgg}
      >
        {isEgg && egg ? (
          <>
            <EggPhaseText>{EGG_PHASE_LABELS[egg.phase] ?? "알"}</EggPhaseText>
            <EggProgressText>
              {egg.records} / {egg.nextPhaseAt}
            </EggProgressText>
          </>
        ) : (
          <>
            {currentForm && <FormName>{currentForm.name}</FormName>}
            {tier && (
              <TierBadge $tierColor={TIER_COLORS[tier]}>
                {TIER_LABELS[tier]}
              </TierBadge>
            )}
          </>
        )}
      </CharacterOrb>

      <CharacterInfoRow>
        <RarityBadge $rarity={color.rarity}>
          {RARITY_LABELS[color.rarity]}
        </RarityBadge>
      </CharacterInfoRow>
    </CharacterDisplayWrapper>
  );
};

export default CharacterDisplay;
