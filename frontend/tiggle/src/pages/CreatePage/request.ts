import { TransactionApiControllerService } from "@/generated";
import { getAxiosInstance } from "@/query/openapi-request";

export interface TransactionFormData {
  dto: {
    categoryId: number;
    amount: number;
    content: string;
    reason: string;
    tagNames: string[];
    date: string;
  };
  files: File[];
}

export interface TransactionUpdateData {
  transactionId: number;
  dto: {
    categoryId: number;
    amount: number;
    content: string;
    reason: string;
    tagNames: string[];
    date: string;
  };
}

export interface AddPhotosData {
  transactionId: number;
  files: File[];
}

export const createTransaction = async (data: TransactionFormData) => {
  const formData = new FormData();

  formData.append("dto", JSON.stringify(data.dto));

  data.files.forEach(file => {
    formData.append("files", file);
  });

  const axiosInstance = getAxiosInstance();
  return axiosInstance.post("/api/v1/transaction", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};

export const updateTransaction = async (data: TransactionUpdateData) => {
  return TransactionApiControllerService.updateTransaction(
    data.transactionId,
    data.dto,
  );
};

export const addTransactionPhotos = async (data: AddPhotosData) => {
  const formData = new FormData();
  data.files.forEach(file => {
    formData.append("files", file);
  });

  const axiosInstance = getAxiosInstance();

  const response = await axiosInstance.post(
    `/api/v1/transaction/${data.transactionId}/photos`,
    formData,
    {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    },
  );

  return response.data;
};
