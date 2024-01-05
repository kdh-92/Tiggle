import { useQuery } from "@tanstack/react-query";
import dayjs from "dayjs";

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
import { TxType } from "@/types";

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
        100, // TODO: Transaction API filtering 기능 개발 후, infinite scroll 구현
      ),
  });

type TxFilter = {
  assetIds: number[];
  categoryIds: number[];
  tagNames: string[];
  type: TxType;
  date: string;
};

export const useTransactionQueryByFilter = (
  memberId: number,
  filter: Partial<TxFilter>,
) => {
  return useQuery({
    queryKey: transactionKeys.list({ memberId, ...filter }),
    queryFn: async () =>
      TransactionApiControllerService.getMemberCountOffsetTransaction(
        memberId,
        0,
        100,
      )
        .then(({ content }) => {
          if (!filter.date) return content;
          return content.filter(data =>
            dayjs(filter.date).isSame(dayjs(data.date), "month"),
          );
        })
        .then(content => {
          if (!filter.assetIds || !filter.assetIds.length) return content;
          return content.filter(data =>
            filter.assetIds.includes(data.asset.id),
          );
        })
        .then(content => {
          if (!filter.categoryIds || !filter.categoryIds.length) return content;
          return content.filter(data =>
            filter.categoryIds.includes(data.category.id),
          );
        })
        .then(content => {
          if (!filter.tagNames || !filter.tagNames.length) return content;
          return content.filter(
            data =>
              data.txTagNames
                ?.split(", ")
                .some(tagName => filter.tagNames.includes(tagName)),
          );
        })
        .then(content => {
          if (!filter.type) return content;
          return content.filter(data => data.type === filter.type);
        }),
  });
};
