import {
  TransactionApiControllerService,
  TransactionRespDto,
  TransactionUpdateReqDto,
} from "@/generated";
import { getAxiosInstance } from "@/query/openapi-request";

export type TransactionFormData = Exclude<
  Parameters<typeof TransactionApiControllerService.createTransaction>[0],
  undefined
>;

export type TransactionUpdateData = {
  transactionId: number;
  dto: TransactionUpdateReqDto;
};

export const createTransaction = async ({
  dto,
  multipartFile,
}: TransactionFormData) => {
  const formData = new FormData();
  formData.append("dto", JSON.stringify(dto));
  if (multipartFile) {
    formData.append("multipartFile", multipartFile);
  }

  return getAxiosInstance()
    .post<TransactionRespDto>("/api/v1/transaction", formData, {
      baseURL: import.meta.env.VITE_API_URL,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then(({ data }) => data);
};

export const updateTransaction = async ({
  transactionId,
  dto,
}: TransactionUpdateData) => {
  return getAxiosInstance()
    .put(`/api/v1/transaction/${transactionId}`, dto)
    .then(({ data }) => data);
};
