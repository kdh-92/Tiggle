import { PayloadAction, createSlice } from "@reduxjs/toolkit";

export interface ContinueUrlState {
  url?: string;
}

const initialState: ContinueUrlState = {};

const continurUrlSlice = createSlice({
  name: "continueUrl",
  initialState,
  reducers: {
    set: (_, action: PayloadAction<string>) => ({ url: action.payload }),
    reset: () => ({ url: undefined }),
  },
});

export default continurUrlSlice;
