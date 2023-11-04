import { TxType } from "@/types";

import { DetailPageActionType } from "./actions";

export interface DetailPageState {
  txType: TxType;
}

const initialState: DetailPageState = {
  txType: "OUTCOME",
};

const detailPageReducer = (
  state = initialState,
  action: DetailPageActionType,
) => {
  switch (action.type) {
    case "SET_TYPE":
      return {
        ...state,
        txType: action.payload,
      };
    default:
      return state;
  }
};

export default detailPageReducer;
