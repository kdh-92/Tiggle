import { Dayjs } from "dayjs";

export interface FilterInputs {
  categoryIds: Array<number>;
  tagNames: Array<string>;
  date: Dayjs;
}
