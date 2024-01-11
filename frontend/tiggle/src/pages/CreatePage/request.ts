import {
  TransactionApiControllerService,
  TransactionRespDto,
} from "@/generated";
import { getAxiosInstance } from "@/query/openapi-request";

export type TransactionFormData = Parameters<
  typeof TransactionApiControllerService.createTransaction
>[0];

export const createTransaction = async ({
  dto,
  multipartFile,
}: TransactionFormData) => {
  const formData = new FormData();
  formData.append("dto", JSON.stringify(dto));
  formData.append("multipartFile", multipartFile);

  return getAxiosInstance()
    .post<TransactionRespDto>("/api/v1/transaction", formData, {
      baseURL: process.env.REACT_APP_API_URL,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then(({ data }) => data);
};
