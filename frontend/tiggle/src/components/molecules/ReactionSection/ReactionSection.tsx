import { useState } from "react";

import ReactionButton, {
  Reaction,
  ReactionType,
} from "@/components/atoms/ReactionButton/ReactionButton";
import { ReactionSummaryDto } from "@/generated";
import { ReactionSectionStyle } from "@/styles/components/ReactionSectionStyle";
import { TxType } from "@/types";

interface ReactionSectionProps
  extends Omit<ReactionSummaryDto, "commentCount"> {
  type: TxType;
  onAddReaction: (reactionType: ReactionType) => void;
  onCancelReaction: () => void;
  className?: string;
}

export default function ReactionSection({
  type,
  upCount,
  downCount,
  onAddReaction,
  onCancelReaction,
  className,
}: ReactionSectionProps) {
  const [selectedReaction, setSelectedReaction] =
    useState<ReactionType>(undefined);

  const handleReactionButtonClick = (inputReaction: ReactionType) => {
    if (selectedReaction === inputReaction) {
      setSelectedReaction(undefined);
      onCancelReaction();
    } else {
      setSelectedReaction(inputReaction);
      onAddReaction(inputReaction);
    }
  };

  return (
    <ReactionSectionStyle className={className}>
      <p className="title">
        이 거래에 대해 <br className="break-m" />
        어떻게 생각하시나요?
      </p>
      <div className="button-wrapper">
        <ReactionButton
          tx={type}
          reaction={Reaction.Up}
          number={upCount}
          onClick={() => handleReactionButtonClick(Reaction.Up)}
          checked={selectedReaction === Reaction.Up}
        />
        <ReactionButton
          tx={type}
          reaction={Reaction.Down}
          number={downCount}
          onClick={() => handleReactionButtonClick(Reaction.Down)}
          checked={selectedReaction === Reaction.Down}
        />
      </div>
    </ReactionSectionStyle>
  );
}
