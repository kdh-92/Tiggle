import { Dayjs } from "dayjs";

import { TxType } from "@/types";

export interface FilterInputs {
  assetIds: Array<number>;
  categoryIds: Array<number>;
  tagIds: Array<number>;
  txType: TxType;
  date: Dayjs;
}
