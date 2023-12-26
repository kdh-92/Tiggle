import { useQuery } from "@tanstack/react-query";

import {
  AssetApiControllerService,
  CategoryApiControllerService,
  TagApiControllerService,
  TransactionApiControllerService,
} from "@/generated";
import {
  assetKeys,
  categoryKeys,
  tagKeys,
  transactionKeys,
} from "@/query/queryKeys";

export const useAllAssetsQuery = () =>
  useQuery({
    queryKey: assetKeys.lists(),
    queryFn: async () => AssetApiControllerService.getAllAsset(),
  });

export const useAllCategoriesQuery = () =>
  useQuery({
    queryKey: categoryKeys.lists(),
    queryFn: async () => CategoryApiControllerService.getAllCategory(),
  });

export const useAllTagsQuery = () =>
  useQuery({
    queryKey: tagKeys.lists(),
    queryFn: async () => TagApiControllerService.getAllDefaultTag(),
  });

export const useTransactionsQueryByMember = (memberId: number) =>
  useQuery({
    queryKey: transactionKeys.list({ memberId }),
    queryFn: async () =>
      TransactionApiControllerService.getMemberCountOffsetTransaction(
        memberId,
        0,
      ),
  });
