export const Tx = {
  Outcome: "OUTCOME",
  Refund: "REFUND",
  Income: "INCOME",
} as const;
export type TxType = (typeof Tx)[keyof typeof Tx];
