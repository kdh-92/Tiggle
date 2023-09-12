export const Tx = {
  Outcome: "OUTCOME",
  Refund: "REFUND",
  Income: "INCOME",
} as const;
export type TxType = (typeof Tx)[keyof typeof Tx];

export const Reaction = {
  Up: "UP",
  Down: "DOWN",
} as const;
export type ReactionType = (typeof Reaction)[keyof typeof Reaction];
