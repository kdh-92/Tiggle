import { MemberApiControllerService, MemberUpdateReqDto } from "@/generated";
import { getAxiosInstance } from "@/query/openapi-request";

export type MemberFormData = Partial<
  Parameters<typeof MemberApiControllerService.updateMe>[0] & {
    xMemberId: number;
  }
>;

export const updateProfile = async ({
  xMemberId,
  dto,
  multipartFile,
}: MemberFormData) => {
  const formData = new FormData();

  if (dto) {
    formData.append("dto", JSON.stringify(dto));
  }

  if (multipartFile) {
    formData.append("multipartFile", multipartFile);
  }

  return getAxiosInstance()
    .put<MemberUpdateReqDto>("/api/v1/member/me", formData, {
      headers: {
        "x-member-id": xMemberId,
        "Content-Type": "multipart/form-data",
      },
    })
    .then(({ data }) => data);
};
