import { Dayjs } from "dayjs";

import { TxType } from "@/types";

export interface FilterInputs {
  categoryIds: Array<number>;
  tagNames: Array<string>;
  txType: TxType;
  date: Dayjs;
}
