import { UseQueryOptions, useQuery } from "@tanstack/react-query";

import {
  AssetApiControllerService,
  CategoryApiControllerService,
  TagApiControllerService,
  TransactionApiControllerService,
  TransactionRespDto,
} from "@/generated";
import {
  assetKeys,
  categoryKeys,
  tagKeys,
  transactionKeys,
} from "@/query/queryKeys";

export const transactionQuery = (id: number) => ({
  queryKey: transactionKeys.detail(id),
  queryFn: async () => TransactionApiControllerService.getTransaction(id),
});

export const useTransactionQuery = (id: number, options: UseQueryOptions) =>
  useQuery<TransactionRespDto>({ ...transactionQuery(id), ...options });

export const useAllAssetsQuery = () =>
  useQuery(assetKeys.lists(), async () =>
    AssetApiControllerService.getAllAsset(),
  );

export const useAllCategoriesQuery = () =>
  useQuery(categoryKeys.lists(), async () =>
    CategoryApiControllerService.getAllCategory(),
  );

export const useAllTagsQuery = () =>
  useQuery(tagKeys.lists(), async () =>
    TagApiControllerService.getAllDefaultTag(),
  );
