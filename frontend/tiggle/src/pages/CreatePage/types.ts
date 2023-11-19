import { Dayjs } from "dayjs";

import { TransactionApiControllerService } from "@/generated";

export type TransactionInputs = {
  assetId: number;
  categoryId: number;
  amount: number;
  content: string;
  reason: string;
  tags: Array<string>;
  date: Dayjs;
  imageUrl: FileList;
};

export type TransactionFormData = Parameters<
  typeof TransactionApiControllerService.createTransaction
>[0];
