import { useState, useCallback, useEffect } from "react";

import { useMutation, useQuery } from "@tanstack/react-query";

import { ReactionApiService, ReactionSummaryRespDto } from "@/generated";
import useAuth from "@/hooks/useAuth";
import ReactionButton from "@/pages/DetailPage/ReactionButton/ReactionButton";
import { ReactionSectionStyle } from "@/pages/DetailPage/ReactionSection/ReactionSectionStyle";
import queryClient from "@/query/queryClient";
import { Reaction, ReactionType } from "@/types";

function useDebounce<T extends (...args: any[]) => void>(
  callback: T,
  delay: number,
): T {
  const [timeoutId, setTimeoutId] = useState<NodeJS.Timeout | null>(null);

  return useCallback(
    ((...args: Parameters<T>) => {
      if (timeoutId) {
        clearTimeout(timeoutId);
      }

      const newTimeoutId = setTimeout(() => {
        callback(...args);
      }, delay);

      setTimeoutId(newTimeoutId);
    }) as T,
    [callback, delay, timeoutId],
  );
}

interface ReactionSectionProps
  extends Omit<ReactionSummaryRespDto, "commentCount"> {
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

  const [optimisticUpCount, setOptimisticUpCount] = useState(upCount || 0);
  const [optimisticDownCount, setOptimisticDownCount] = useState(
    downCount || 0,
  );

  const { data: myReactionData } = useQuery({
    queryKey: ["reaction", "my", txId],
    queryFn: async () => {
      try {
        const response = await ReactionApiService.getReaction(txId);
        return response.data;
      } catch (error) {
        return null;
      }
    },
  });

  useEffect(() => {
    if (myReactionData) {
      setSelectedReaction(myReactionData.type);
    } else {
      setSelectedReaction(undefined);
    }
  }, [myReactionData]);

  const { mutate: upsertReaction } = useMutation(
    async (type: ReactionType) =>
      ReactionApiService.upsertReaction(txId, { type }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["reaction", "detail", txId]);
        queryClient.invalidateQueries(["reaction", "my", txId]);
      },
      onError: () => {
        setOptimisticUpCount(upCount || 0);
        setOptimisticDownCount(downCount || 0);
        setSelectedReaction(myReactionData?.type || undefined);
      },
    },
  );

  const { mutate: deleteReaction } = useMutation(
    async () => ReactionApiService.deleteReaction(txId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["reaction", "detail", txId]);
        queryClient.invalidateQueries(["reaction", "my", txId]);
      },
      onError: () => {
        setOptimisticUpCount(upCount || 0);
        setOptimisticDownCount(downCount || 0);
        setSelectedReaction(myReactionData?.type || undefined);
      },
    },
  );

  const executeReactionChange = useCallback(
    (inputReaction: ReactionType) => {
      if (selectedReaction === inputReaction) {
        deleteReaction();
      } else {
        upsertReaction(inputReaction);
      }
    },
    [selectedReaction, deleteReaction, upsertReaction],
  );

  const debouncedExecuteReaction = useDebounce(executeReactionChange, 500);

  const updateOptimisticUI = useCallback(
    (inputReaction: ReactionType) => {
      if (selectedReaction === inputReaction) {
        setSelectedReaction(undefined);
        if (inputReaction === Reaction.Up) {
          setOptimisticUpCount(prev => Math.max(0, prev - 1));
        } else {
          setOptimisticDownCount(prev => Math.max(0, prev - 1));
        }
      } else {
        if (selectedReaction) {
          if (selectedReaction === Reaction.Up) {
            setOptimisticUpCount(prev => Math.max(0, prev - 1));
          } else {
            setOptimisticDownCount(prev => Math.max(0, prev - 1));
          }
        }
        setSelectedReaction(inputReaction);
        if (inputReaction === Reaction.Up) {
          setOptimisticUpCount(prev => prev + 1);
        } else {
          setOptimisticDownCount(prev => prev + 1);
        }
      }
    },
    [selectedReaction],
  );

  const handleReactionButtonClick = useCallback(
    (inputReaction: ReactionType) => {
      updateOptimisticUI(inputReaction);
      debouncedExecuteReaction(inputReaction);
    },
    [updateOptimisticUI, debouncedExecuteReaction],
  );

  return (
    <ReactionSectionStyle className={className}>
      <p className="title">
        이 거래에 대해 <br className="break-m" />
        어떻게 생각하시나요?
      </p>
      <div className="button-wrapper">
        <ReactionButton
          reaction={Reaction.Up}
          number={optimisticUpCount}
          onClick={() =>
            checkIsLogin(() => handleReactionButtonClick(Reaction.Up))
          }
          checked={selectedReaction === Reaction.Up}
        />
        <ReactionButton
          reaction={Reaction.Down}
          number={optimisticDownCount}
          onClick={() =>
            checkIsLogin(() => handleReactionButtonClick(Reaction.Down))
          }
          checked={selectedReaction === Reaction.Down}
        />
      </div>
    </ReactionSectionStyle>
  );
}
