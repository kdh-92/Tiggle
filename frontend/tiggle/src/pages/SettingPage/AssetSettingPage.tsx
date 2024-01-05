import { useCallback } from "react";

import { useMutation } from "@tanstack/react-query";

import { Loading } from "@/components/atoms";
import { AssetApiControllerService } from "@/generated";
import useMessage from "@/hooks/useMessage";
import queryClient from "@/query/queryClient";
import { assetKeys } from "@/query/queryKeys";
import withAuth, { AuthProps } from "@/utils/withAuth";

import SettingForm from "./SettingForm/SettingForm";
import { useAssetsQuery } from "./query";
import { MypageDetailPageStyle } from "../MyProfilePage/MyDetailPageCommonStyle";

interface AssetSettingPageProps extends AuthProps {}

const AssetSettingPage = ({}: AssetSettingPageProps) => {
  const messageApi = useMessage();
  const { data, isLoading: isDataLoading } = useAssetsQuery();
  const { mutate: createMutate } = useMutation(async (name: string) =>
    AssetApiControllerService.createAsset({ name }),
  );

  const create = useCallback(
    (
      value: string,
      callback?: (data: { id: number; name: string }) => void,
    ) => {
      return createMutate(value, {
        onSuccess: ({ id, name }) => {
          callback?.({ id, name });
          queryClient.invalidateQueries({ queryKey: assetKeys.lists() });
          messageApi.open({ type: "success", content: "자산 생성 성공" });
        },
        onError: () => {
          messageApi.open({ type: "error", content: "자산 생성 실패" });
        },
      });
    },
    [createMutate],
  );

  const update = useCallback(
    (
      sid: number,
      newValue: string,
      callback?: (data: { id: number; name: string }) => void,
    ) => {
      // TODO: API 연결
      callback?.({ id: sid, name: newValue });
      console.log(`ASSET PAGE - update api called`, sid, newValue);
      messageApi.open({ type: "success", content: "자산 수정 성공" });
    },
    [],
  );

  const remove = useCallback(
    (sid: number, callback?: (data: { id: number; name: string }) => void) => {
      // TODO: API 연결
      callback({ id: sid, name: "temp" });
      console.log(`ASSET PAGE - remove api called`, sid);
      messageApi.open({ type: "success", content: "자산 삭제 성공" });
    },
    [],
  );

  return (
    <MypageDetailPageStyle>
      <p className="page-title">자산 관리</p>

      {isDataLoading && <Loading />}
      {!isDataLoading && data && (
        <SettingForm
          data={data.map(({ id, name }) => ({ sid: id, name }))}
          requests={{ create, update, remove }}
        />
      )}
    </MypageDetailPageStyle>
  );
};

export default withAuth(AssetSettingPage);
