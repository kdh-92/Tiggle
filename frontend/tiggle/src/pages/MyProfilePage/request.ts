import { MemberApiControllerService, MemberResponseDto } from "@/generated";
import { getAxiosInstance } from "@/query/openapi-request";

export type MemberFormData = Partial<
  Parameters<typeof MemberApiControllerService.updateMe>[0] & {
    xMemberId: number;
  }
>;

export const updateProfile = async ({
  xMemberId,
  memberRequestDto,
  multipartFile,
}: MemberFormData) => {
  const formData = new FormData();

  if (memberRequestDto) {
    formData.append("memberRequestDto", JSON.stringify(memberRequestDto));
  }

  if (multipartFile) {
    formData.append("multipartFile", multipartFile);
  }

  return getAxiosInstance()
    .put<MemberResponseDto>("/api/v1/member/me", formData, {
      headers: {
        "x-member-id": xMemberId,
        "Content-Type": "multipart/form-data",
      },
    })
    .then(({ data }) => data);
};
