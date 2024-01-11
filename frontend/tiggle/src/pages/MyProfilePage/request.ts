import { MemberApiControllerService, MemberDto } from "@/generated";
import { getAxiosInstance } from "@/query/openapi-request";

export type MemberFormData = Parameters<
  typeof MemberApiControllerService.updateMe
>[1] & { xMemberId: number };

export const updateProfile = async ({
  xMemberId,
  memberDto,
  multipartFile,
}: MemberFormData) => {
  const formData = new FormData();
  formData.append("memberDto", JSON.stringify(memberDto));
  formData.append("multipartFile", multipartFile);

  return getAxiosInstance()
    .put<MemberDto>("/api/v1/member/me", formData, {
      headers: {
        "x-member-id": xMemberId,
        "Content-Type": "multipart/form-data",
      },
    })
    .then(({ data }) => data);
};
