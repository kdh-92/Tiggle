import { TransactionRespDto } from "@/generated";

export const setTransactionData = (data: TransactionRespDto[]) => ({
  type: "SET_TRANSACTION_DATA",
  payload: data,
});

export const setIsError = (isError: boolean) => ({
  type: "SET_IS_ERROR",
  payload: isError,
});

export const setIsLoading = (isLoading: boolean) => ({
  type: "SET_IS_LOADING",
  payload: isLoading,
});
