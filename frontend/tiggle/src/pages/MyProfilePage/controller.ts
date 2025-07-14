import { useForm } from "react-hook-form";
import { useLoaderData, useNavigate } from "react-router-dom";

import { QueryClient, useMutation, useQuery } from "@tanstack/react-query";
import dayjs from "dayjs";

import useUpload from "@/components/atoms/Upload/useUpload";
import { MemberApiControllerService } from "@/generated";
import useMessage from "@/hooks/useMessage";
import { getProfileImageUrl } from "@/utils/imageUrl";

import { MemberFormData, updateProfile } from "./request";

import type { ProfileInputs } from "./types";

const profileQuery = () => ({
  queryKey: ["profile", "me"],
  queryFn: async () => MemberApiControllerService.getMe(),
});

export const loader = (queryClient: QueryClient) => () =>
  queryClient.ensureQueryData(profileQuery());

export const useProfilePage = () => {
  const messageApi = useMessage();
  const navigate = useNavigate();

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
      nickname: profileData.data.nickname,
      email: profileData.data.email,
      birth: profileData.data.birth ? dayjs(profileData.data.birth) : null,
    },
  });
  const profileUrlRegister = register("profileUrl");
  const isDirty =
    (_isDirty && Object.keys(dirtyFields).length > 0) || file !== null;

  const { imageUrl, handleUpload, handleReset, file } = useUpload({
    onReset: () => resetField("profileUrl"),
    defaultUrl: getProfileImageUrl(profileData?.data?.profileUrl),
  });

  const handleSubmit = _handleSubmit(({ nickname, birth }: ProfileInputs) => {
    if (Object.keys(dirtyFields).length === 0 && !file) {
      return;
    }

    const formData: MemberFormData = {
      dto: {
        ...(dirtyFields["nickname"] && { nickname }),
        ...(dirtyFields["birth"] && { birth: dayjs(birth).toISOString() }),
      },
      multipartFile: file,
    };

    mutate(formData, {
      onSuccess: () => {
        messageApi.open({
          type: "success",
          content: "프로필 수정이 완료되었습니다.",
        });
        refetchProfileData().then(({ data }) => {
          reset({
            nickname: data!.data.nickname,
            email: data!.data.email,
            birth: dayjs(data!.data.birth),
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
  });

  const handleCancel = () => {
    navigate(-1);
  };

  return {
    control,
    profileUrlRegister,
    isDirty,
    profileUrl: imageUrl,
    handleSubmit,
    handleUpload,
    handleResetImage: handleReset,
    handleCancel,
  };
};
