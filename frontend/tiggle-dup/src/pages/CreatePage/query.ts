import { useQuery } from "@tanstack/react-query";

import {
  AssetApiControllerService,
  CategoryApiControllerService,
  TagApiControllerService,
} from "@/generated";
import { assetKeys, categoryKeys, tagKeys } from "@/query/queryKeys";

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
