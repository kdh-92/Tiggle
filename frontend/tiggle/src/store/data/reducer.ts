import { TransactionRespDto } from "@/generated";

export interface DataState {
  transactionData: TransactionRespDto[];
  isError: boolean;
  isLoading: boolean;
}

interface DataAction {
  type: string;
  payload: any;
}

const initialState: DataState = {
  transactionData: [],
  isError: false,
  isLoading: false,
};

const getDataReducer = (state = initialState, action: DataAction) => {
  switch (action.type) {
    case "SET_TRANSACTION_DATA":
      return {
        ...state,
        transactionData: action.payload,
      };
    case "SET_IS_ERROR":
      return {
        ...state,
        isError: action.payload,
      };
    case "SET_IS_LOADING":
      return {
        ...state,
        sLoading: action.payload,
      };
    default:
      return state;
  }
};

export default getDataReducer;
