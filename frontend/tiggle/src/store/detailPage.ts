import { createSlice } from "@reduxjs/toolkit";

import { TxType } from "@/types";

import type { PayloadAction } from "@reduxjs/toolkit";

export interface DetailPageState {
  txType: TxType;
}

const initialState: DetailPageState = {
  txType: "OUTCOME",
};

const detailPageSlice = createSlice({
  name: "detailPage",
  initialState,
  reducers: {
    setType: (state, action: PayloadAction<TxType>) => ({
      ...state,
      txType: action.payload,
    }),
  },
});

export default detailPageSlice;
