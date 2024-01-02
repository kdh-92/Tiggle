import { useForm } from "react-hook-form";
import { useLoaderData } from "react-router-dom";

import { QueryClient, useMutation, useQuery } from "@tanstack/react-query";
import dayjs from "dayjs";

import useUpload from "@/components/atoms/Upload/useUpload";
import { MemberApiControllerService } from "@/generated";
import { useMessage } from "@/templates/GeneralTemplate";

import { MemberFormData, updateProfile } from "./request";

import type { ProfileInputs } from "./types";

const TEMP_MEMBER_ID = 4;

const profileQuery = () => ({
  queryKey: ["profile", "me"],
  queryFn: async () => MemberApiControllerService.getMe(TEMP_MEMBER_ID),
});

export const loader = (queryClient: QueryClient) => () =>
  queryClient.ensureQueryData(profileQuery());

export const useProfilePage = () => {
  const { messageApi } = useMessage();

  const initialData = useLoaderData() as Awaited<
    ReturnType<ReturnType<typeof loader>>
  >;
  const { data: profileData, refetch: refetchProfileData } = useQuery({
    ...profileQuery(),
    initialData,
  });

  const { mutate } = useMutation(updateProfile);

  const {
    control,
    register,
    reset,
    resetField,
    handleSubmit: _handleSubmit,
    formState: { isDirty: _isDirty, dirtyFields },
  } = useForm<ProfileInputs>({
    defaultValues: {
      nickname: profileData.nickname,
      email: profileData.email,
      birth: dayjs(profileData.birth),
    },
  });
  const profileUrlRegister = register("profileUrl");
  const isDirty = _isDirty && Object.keys(dirtyFields).length > 0;

  const { imageUrl, handleUpload, handleReset } = useUpload({
    onReset: () => resetField("profileUrl"),
    defaultUrl: profileData?.profileUrl,
  });

  const handleSubmit = _handleSubmit(
    ({ nickname, birth, email, profileUrl }: ProfileInputs) => {
      if (Object.keys(dirtyFields).length === 0) {
        return;
      }

      const formData: MemberFormData = {
        // TODO: xMemberId 삭제
        xMemberId: TEMP_MEMBER_ID,
        memberDto: {
          ...(dirtyFields["nickname"] && { nickname }),
          ...(dirtyFields["email"] && { email }),
          ...(dirtyFields["birth"] && { birth: dayjs(birth).toISOString() }),
        },
        multipartFile: dirtyFields["profileUrl"] && profileUrl[0],
      };

      mutate(formData, {
        onSuccess: () => {
          messageApi.open({
            type: "success",
            content: "프로필 수정이 완료되었습니다.",
          });
          refetchProfileData().then(({ data }) => {
            reset({
              nickname: data.nickname,
              email: data.email,
              birth: dayjs(data.birth),
            });
          });
        },
        onError: () => {
          messageApi.open({
            type: "error",
            content: "프로필 수정에 실패했습니다.",
          });
        },
      });
    },
  );

  return {
    control,
    profileUrlRegister,
    isDirty,
    profileUrl: imageUrl,
    handleSubmit,
    handleUpload,
    handleResetImage: handleReset,
  };
};