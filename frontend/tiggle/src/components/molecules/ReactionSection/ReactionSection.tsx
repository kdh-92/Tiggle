import { useState } from "react";

import ReactionButton, {
  Reaction,
  ReactionType,
} from "@/components/atoms/ReactionButton/ReactionButton";
import { ReactionSectionStyle } from "@/styles/components/ReactionSectionStyle";
import { TxType } from "@/types";

interface ReactionSectionProps {
  txType: TxType;
  reactions: Record<ReactionType, number>;
  onAddReaction: (reactionType: ReactionType) => void;
  onCancelReaction: () => void;
  className?: string;
}

export default function ReactionSection({
  txType,
  reactions,
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
          tx={txType}
          reaction={Reaction.Up}
          number={reactions.Up}
          onClick={() => handleReactionButtonClick(Reaction.Up)}
          checked={selectedReaction === Reaction.Up}
        />
        <ReactionButton
          tx={txType}
          reaction={Reaction.Down}
          number={reactions.Down}
          onClick={() => handleReactionButtonClick(Reaction.Down)}
          checked={selectedReaction === Reaction.Down}
        />
      </div>
    </ReactionSectionStyle>
  );
}
