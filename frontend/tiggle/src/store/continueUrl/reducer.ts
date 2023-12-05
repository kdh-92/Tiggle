import { ContinueUrlActionType, type } from "./actions";

export interface ContinueUrlState {
  url?: string;
}

const initialState: ContinueUrlState = {};

const continueUrlReducer = (
  state = initialState,
  action: ContinueUrlActionType,
) => {
  switch (action.type) {
    case type.SET_CONTINUE_URL:
      return {
        url: action.payload,
      };
    case type.RESET_CONTINUE_URL:
      return {
        url: undefined,
      };
    default:
      return state;
  }
};

export default continueUrlReducer;
