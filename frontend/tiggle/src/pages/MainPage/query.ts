import { useQuery } from "@tanstack/react-query";

import { TransactionApiControllerService } from "@/generated";
import { transactionKeys } from "@/query/queryKeys";

export const useAllTransactionsQuery = () =>
  useQuery({
    queryKey: transactionKeys.lists(),
    queryFn: () => TransactionApiControllerService.getAllTransaction(),
  });
