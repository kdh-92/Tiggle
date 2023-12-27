import { Dayjs } from "dayjs";

import { TxType } from "@/types";

export interface FilterInputs {
  assetIds: Array<number>;
  categoryIds: Array<number>;
  tagNames: Array<string>;
  txType: TxType;
  date: Dayjs;
}
