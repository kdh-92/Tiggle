export const Tx = {
  OUTCOME: "OUTCOME",
  REFUND: "REFUND",
  INCOME: "INCOME",
} as const;
export type TxType = (typeof Tx)[keyof typeof Tx];
