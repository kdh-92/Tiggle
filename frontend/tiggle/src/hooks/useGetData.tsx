import { useEffect } from "react";
import { useDispatch } from "react-redux";

import { useQuery } from "@tanstack/react-query";

import { TransactionApiControllerService } from "@/generated";
import {
  setIsError,
  setIsLoading,
  setTransactionData,
} from "@/store/data/actions";

export default function useGetData() {
  const dispatch = useDispatch();

  const { data, isError, isLoading } = useQuery({
    queryKey: ["data"],
    queryFn: () => TransactionApiControllerService.getAllTransaction(),
  });

  useEffect(() => {
    dispatch(setTransactionData(data));
    dispatch(setIsError(isError));
    dispatch(setIsLoading(isLoading));
  });

  return { data, isError, isLoading };
}
