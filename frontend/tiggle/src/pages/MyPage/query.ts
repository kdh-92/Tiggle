import { UseQueryOptions, useQuery } from "@tanstack/react-query";

import {
  PageTransactionRespDto,
  TransactionApiControllerService,
} from "@/generated";
import { transactionKeys } from "@/query/queryKeys";

export const useTransactionsQueryByMember = (
  memberId: number,
  index: number,
  options?: UseQueryOptions,
) =>
  useQuery<PageTransactionRespDto>(
    transactionKeys.list({ memberId }),
    async () =>
      TransactionApiControllerService.getMemberCountOffsetTransaction(
        memberId,
        index,
      ),
    {
      ...options,
      staleTime: 1000 * 60 * 10,
    },
  );
