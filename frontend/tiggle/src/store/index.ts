import { combineReducers, configureStore } from "@reduxjs/toolkit";
import {
  persistReducer,
  persistStore,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
} from "redux-persist";
import storage from "redux-persist/lib/storage"; // defaults to localStorage for web

import continueUrl from "@/store/continueUrl";
import detailPage from "@/store/detailPage";
import notificationModal from "@/store/notificationModal";

const persistConfig = {
  key: "root",
  storage,
  version: 1,
  whitelist: ["continueUrl"],
};

const reducers = combineReducers({
  detailPage: detailPage.reducer,
  continueUrl: continueUrl.reducer,
  notificationModal: notificationModal.reducer,
});
type Reducers = ReturnType<typeof reducers>;
const persistedReducer = persistReducer<Reducers>(persistConfig, reducers);

export const store = configureStore({
  reducer: persistedReducer,
  middleware: getDefaultMiddleware =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, REGISTER, PAUSE, PERSIST, PURGE],
      },
    }),
  devTools: import.meta.env.DEV,
});
export const persistedStore = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
