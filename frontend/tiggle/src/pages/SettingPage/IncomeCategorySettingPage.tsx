import { useCallback } from "react";

import { useMutation, useQuery } from "@tanstack/react-query";

import { Loading } from "@/components/atoms";
import { CategoryApiControllerService } from "@/generated";
import useMessage from "@/hooks/useMessage";
import queryClient from "@/query/queryClient";
import { categoryKeys } from "@/query/queryKeys";
import { Tx } from "@/types";
import withAuth, { AuthProps } from "@/utils/withAuth";

import SettingForm from "./SettingForm/SettingForm";
import { MypageDetailPageStyle } from "../MyProfilePage/MyDetailPageCommonStyle";

interface IncomeCategorySettingPageProps extends AuthProps {}

const IncomeCategorySettingPage = ({}: IncomeCategorySettingPageProps) => {
  const messageApi = useMessage();
  const { data: categoriesData, isLoading } = useQuery({
    queryKey: categoryKeys.list({ type: Tx.INCOME }),
    queryFn: async () => CategoryApiControllerService.getCategory1(Tx.INCOME),
  });
  const { mutate: createMutate } = useMutation(async (name: string) =>
    CategoryApiControllerService.createCategory({ name }),
  );

  const create = useCallback(
    (
      value: string,
      callback?: (data: { id: number; name: string }) => void,
    ) => {
      createMutate(value, {
        onSuccess: ({ id, name }) => {
          callback?.({ id: id!, name: name! });
          queryClient.invalidateQueries({
            queryKey: categoryKeys.list({ type: Tx.INCOME }),
          });
          messageApi.open({ type: "success", content: "카테고리 생성 성공" });
        },
        onError: () => {
          messageApi.open({ type: "error", content: "카테고리 생성 실패" });
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
      console.log(`INCOME CATEGORY PAGE - update api called`, sid, newValue);
      messageApi.open({ type: "success", content: "카테고리 수정 성공" });
    },
    [],
  );

  const remove = useCallback(
    (sid: number, callback?: (data: { id: number; name: string }) => void) => {
      // TODO: API 연결
      callback?.({ id: sid, name: "temp" });
      console.log(`INCOME CATEGORY PAGE - delete api called`, sid);
      messageApi.open({ type: "success", content: "카테고리 삭제 성공" });
    },
    [],
  );

  return (
    <MypageDetailPageStyle>
      <p className="page-title">수입 카테고리 관리</p>

      {isLoading && <Loading />}
      {!isLoading && categoriesData && (
        <SettingForm
          data={categoriesData.map(({ id, name }) => ({
            sid: id!,
            name: name!,
          }))}
          requests={{ create, update, remove }}
        />
      )}
    </MypageDetailPageStyle>
  );
};

export default withAuth(IncomeCategorySettingPage);
