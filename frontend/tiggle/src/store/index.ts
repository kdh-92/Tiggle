import { configureStore } from "@reduxjs/toolkit";

import getDataReducer from "@/store/data/reducer";
import detailPageStore from "@/store/detailPage";

export const store = configureStore({
  reducer: {
    data: getDataReducer,
    detailPage: detailPageStore.reducer,
  },
  devTools: process.env.NODE_ENV !== "production",
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
