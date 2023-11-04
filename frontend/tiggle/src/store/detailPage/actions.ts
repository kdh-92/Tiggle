import { TxType } from "@/types";

const SET_TYPE = "SET_TYPE" as const;
export const type = { SET_TYPE };
type ActionType = (typeof type)[keyof typeof type];

const setType = (type: TxType) => ({ type: SET_TYPE, payload: type });
export const creators = { setType };

export type DetailPageActionType = {
  type: ActionType;
  payload: TxType;
};
