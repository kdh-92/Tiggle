const SET_CONTINUE_URL = "SET_CONTINUE_URL" as const;
const GET_CONTINUE_URL = "GET_CONTINUE_URL" as const;
const RESET_CONTINUE_URL = "RESET_CONTINUE_URL" as const;
export const type = { SET_CONTINUE_URL, GET_CONTINUE_URL, RESET_CONTINUE_URL };
type ActionType = (typeof type)[keyof typeof type];

const set = (url: string) => ({ type: SET_CONTINUE_URL, payload: url });
const get = () => ({ type: GET_CONTINUE_URL });
const reset = () => ({ type: RESET_CONTINUE_URL });
export const creators = { set, get, reset };

export type ContinueUrlActionType = {
  type: ActionType;
  payload?: string;
};
