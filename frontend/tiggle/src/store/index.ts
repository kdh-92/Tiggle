import { configureStore } from "@reduxjs/toolkit";

import getDataReducer from "@/redux/reducer/getDataReducer";

export const store = configureStore({
  reducer: {
    data: getDataReducer,
  },
  devTools: process.env.NODE_ENV !== "production",
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
