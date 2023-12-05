import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { persistReducer, persistStore } from "redux-persist";
import storage from "redux-persist/lib/storage"; // defaults to localStorage for web

import continueUrl from "@/store/continueUrl";
import getDataReducer from "@/store/data/reducer";
import detailPageStore from "@/store/detailPage";

const persistConfig = {
  key: "root",
  storage,
  whitelist: ["continueUrl"],
};

const reducers = combineReducers({
  data: getDataReducer,
  detailPage: detailPageStore.reducer,
  continueUrl: continueUrl.reducer,
});
const persistedReducer = persistReducer(persistConfig, reducers);

export const store = configureStore({
  reducer: persistedReducer,
  devTools: process.env.NODE_ENV !== "production",
});
export const persistedStore = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
