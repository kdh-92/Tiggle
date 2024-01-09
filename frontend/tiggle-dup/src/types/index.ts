export const Tx = {
  INCOME: "INCOME",
  OUTCOME: "OUTCOME",
  REFUND: "REFUND",
} as const;
export type TxType = (typeof Tx)[keyof typeof Tx];

export const Reaction = {
  Up: "UP",
  Down: "DOWN",
} as const;
export type ReactionType = (typeof Reaction)[keyof typeof Reaction];
