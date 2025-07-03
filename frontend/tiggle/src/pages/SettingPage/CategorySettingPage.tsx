import { useCallback } from "react";

import { useMutation, useQuery } from "@tanstack/react-query";

import { Loading } from "@/components/atoms";
import { CategoryApiControllerService } from "@/generated";
import useAuth from "@/hooks/useAuth";
import useMessage from "@/hooks/useMessage";
import queryClient from "@/query/queryClient";
import { categoryKeys } from "@/query/queryKeys";
// import { Tx } from "@/types";
import withAuth, { AuthProps } from "@/utils/withAuth";

import SettingForm from "./SettingForm/SettingForm";
import { MypageDetailPageStyle } from "../MyProfilePage/MyDetailPageCommonStyle";

interface CategorySettingPageProps extends AuthProps {}

const CategorySettingPage = ({}: CategorySettingPageProps) => {
  const messageApi = useMessage();
  const { profile } = useAuth();
  const { data: categoriesData, isLoading } = useQuery({
    // queryKey: categoryKeys.list({ type: Tx.OUTCOME }),
    // queryFn: async () => CategoryApiControllerService.getCategory(Tx.OUTCOME),
    queryKey: categoryKeys.list(),
    queryFn: async () =>
      CategoryApiControllerService.getCategoryByMemberIdOrDefaults(
        profile?.id || 0,
      ),
    enabled: !!profile?.id,
  });
  // const { mutate: createMutate } = useMutation(async (name: string) =>
  //   CategoryApiControllerService.createCategory({ name }),
  // );

  const { mutate: createMutate } = useMutation(async (name: string) =>
    CategoryApiControllerService.createCategory(profile?.id || 0, {
      name,
      // type: Tx.OUTCOME,
      defaults: true,
    }),
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
            // queryKey: categoryKeys.list({ type: Tx.OUTCOME }),
            queryKey: categoryKeys.list(),
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
      CategoryApiControllerService.updateCategory(sid, {
        name: newValue,
        defaults: true,
      })
        .then(() => {
          callback?.({ id: sid, name: newValue });
          queryClient.invalidateQueries({
            queryKey: categoryKeys.list(),
          });
          messageApi.open({ type: "success", content: "카테고리 수정 성공" });
        })
        .catch(() => {
          messageApi.open({ type: "error", content: "카테고리 수정 실패" });
        });
    },
    [],
  );

  const remove = useCallback(
    (sid: number, callback?: (data: { id: number; name: string }) => void) => {
      CategoryApiControllerService.deleteCategory(sid)
        .then(() => {
          callback?.({ id: sid, name: "temp" });
          queryClient.invalidateQueries({
            queryKey: categoryKeys.list({}),
          });
          messageApi.open({ type: "success", content: "카테고리 삭제 성공" });
        })
        .catch(() => {
          messageApi.open({ type: "error", content: "카테고리 삭제 실패" });
        });
    },
    [],
  );

  return (
    <MypageDetailPageStyle>
      <p className="page-title">카테고리 관리</p>

      {isLoading && <Loading />}
      {!isLoading && categoriesData && (
        <SettingForm
          data={categoriesData.categories?.map(({ id, name }) => ({
            sid: id!,
            name: name!,
          }))}
          requests={{ create, update, remove }}
        />
      )}
    </MypageDetailPageStyle>
  );
};

export default withAuth(CategorySettingPage);
