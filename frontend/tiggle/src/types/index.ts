export const Tx = {
  Outcome: "outcome",
  Refund: "refund",
} as const;
export type TxType = (typeof Tx)[keyof typeof Tx];
