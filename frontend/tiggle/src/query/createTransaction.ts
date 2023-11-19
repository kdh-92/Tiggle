import axios from "axios";

import { TransactionRespDto } from "@/generated";
import { TransactionFormData } from "@/pages/CreatePage/types";

export const createTransaction = async ({
  dto,
  multipartFile,
}: TransactionFormData) => {
  const formData = new FormData();
  formData.append("dto", JSON.stringify(dto));
  formData.append("multipartFile", multipartFile);

  return axios
    .post<TransactionRespDto>("/api/v1/transaction", formData, {
      baseURL: process.env.REACT_APP_API_URL,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then(({ data }) => data);
};
