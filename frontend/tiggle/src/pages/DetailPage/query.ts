import { UseQueryOptions, useQuery } from "@tanstack/react-query";

import {
  CommentApiService,
  PageCommentRespDto,
  ReactionApiService,
  TransactionApiControllerService,
  TransactionRespDto,
} from "@/generated";
import { commentKeys, reactionKeys, transactionKeys } from "@/query/queryKeys";

export const transactionQuery = (id: number) => ({
  queryKey: transactionKeys.detail(id),
  queryFn: async () => TransactionApiControllerService.getTransaction(id),
});

export const useTransactionQuery = (id: number, options: UseQueryOptions) =>
  useQuery<TransactionRespDto>({ ...transactionQuery(id), ...options });

export const useReactionQuery = (id: number) =>
  useQuery({
    queryKey: reactionKeys.detail(id),
    queryFn: async () => ReactionApiService.getReactionSummary(id),
  });

export const useCommentsQuery = (txId: number) =>
  useQuery({
    queryKey: commentKeys.list(txId),
    queryFn: async () =>
      TransactionApiControllerService.getAllCommentsByTx(txId),
  });

export const useCommentRepliesQuery = (
  commentId: number,
  options?: UseQueryOptions,
) =>
  useQuery<PageCommentRespDto>({
    queryKey: commentKeys.reply(commentId),
    queryFn: async () => CommentApiService.getAllCommentsByCommentId(commentId),
    staleTime: 1000 * 60 * 10,
    ...options,
  });
