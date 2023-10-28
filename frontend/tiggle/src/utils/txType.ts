import { TxType } from "@/types";

export const convertTxTypeToWord = (txType: TxType) => {
  switch (txType) {
    case "OUTCOME":
      return "지출";
    case "INCOME":
      return "수익";
    case "REFUND":
      return "환불";
  }
};

export const convertTxTypeToColor = (txType: TxType) => {
  switch (txType) {
    case "OUTCOME":
      return "peach";
    case "INCOME":
      return "green";
    case "REFUND":
      return "blue";
  }
};
