import { TransactionApiControllerService } from "@/generated";
import { transactionKeys } from "@/query/queryKeys";
import { useQuery } from "@tanstack/react-query";

export const useAllTransactionsQuery = () => useQuery({
  queryKey: transactionKeys.lists(),
  queryFn: () => TransactionApiControllerService.getAllTransaction(),
});