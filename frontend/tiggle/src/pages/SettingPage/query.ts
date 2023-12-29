import { useQuery } from "@tanstack/react-query";

import { AssetApiControllerService } from "@/generated";
import { assetKeys } from "@/query/queryKeys";

export const useAssetsQuery = () =>
  useQuery({
    queryKey: assetKeys.lists(),
    queryFn: async () => AssetApiControllerService.getAllAsset(),
  });
