import { createSlice } from "@reduxjs/toolkit";

export interface DetailPageState {}

const initialState: DetailPageState = {};

const detailPageSlice = createSlice({
  name: "detailPage",
  initialState,
  reducers: {},
});

export default detailPageSlice;
