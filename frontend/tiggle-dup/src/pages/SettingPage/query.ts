import { useQuery } from "@tanstack/react-query";

import {
  AssetApiControllerService,
  CategoryApiControllerService,
} from "@/generated";
import { assetKeys, categoryKeys } from "@/query/queryKeys";
import { TxType } from "@/types";

export const useAssetsQuery = () =>
  useQuery({
    queryKey: assetKeys.lists(),
    queryFn: async () => AssetApiControllerService.getAllAsset(),
  });

export const useCategoryQueryByTx = (type: Exclude<TxType, "REFUND">) =>
  useQuery({
    queryKey: categoryKeys.list({ type }),
    queryFn: async () => CategoryApiControllerService.getCategory1(type),
  });
