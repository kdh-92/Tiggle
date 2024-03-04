import { useState } from "react";

import { useMutation } from "@tanstack/react-query";

import { ReactionApiService, ReactionSummaryDto } from "@/generated";
import useAuth from "@/hooks/useAuth";
import ReactionButton from "@/pages/DetailPage/ReactionButton/ReactionButton";
import { ReactionSectionStyle } from "@/pages/DetailPage/ReactionSection/ReactionSectionStyle";
import queryClient from "@/query/queryClient";
import { Reaction, ReactionType } from "@/types";

interface ReactionSectionProps
  extends Omit<ReactionSummaryDto, "commentCount"> {
  txId: number;
  className?: string;
}

export default function ReactionSection({
  txId,
  upCount,
  downCount,
  className,
}: ReactionSectionProps) {
  const { checkIsLogin } = useAuth();
  const [selectedReaction, setSelectedReaction] = useState<
    ReactionType | undefined
  >(undefined);

  const { mutate: upsertReaction } = useMutation(
    async (type: ReactionType) =>
      ReactionApiService.upsertReaction(txId, { type }),
    {
      onSuccess: () =>
        queryClient.invalidateQueries(["reaction", "detail", txId]),
    },
  );
  const { mutate: deleteReaction } = useMutation(
    async () => ReactionApiService.deleteReaction(txId),
    {
      onSuccess: () =>
        queryClient.invalidateQueries(["reaction", "detail", txId]),
    },
  );

  const handleReactionButtonClick = (inputReaction: ReactionType) => {
    if (selectedReaction === inputReaction) {
      deleteReaction(undefined, {
        onSuccess: () => setSelectedReaction(undefined),
      });
    } else {
      upsertReaction(inputReaction, {
        onSuccess: () => setSelectedReaction(inputReaction),
      });
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
          reaction={Reaction.Up}
          number={upCount!}
          onClick={() =>
            checkIsLogin(() => handleReactionButtonClick(Reaction.Up))
          }
          checked={selectedReaction === Reaction.Up}
        />
        <ReactionButton
          reaction={Reaction.Down}
          number={downCount!}
          onClick={() =>
            checkIsLogin(() => handleReactionButtonClick(Reaction.Down))
          }
          checked={selectedReaction === Reaction.Down}
        />
      </div>
    </ReactionSectionStyle>
  );
}
