import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";

import CreatePage, {
  loader as createRefundPageLoader,
} from "@/pages/CreatePage";
import DetailPage, { loader as detailPageLoader } from "@/pages/DetailPage";
import LoginPage from "@/pages/LoginPage";
import LoginRedirectPage from "@/pages/LoginRedirectPage";
import MainPage from "@/pages/MainPage";
import MyPage from "@/pages/MyPage";
import MyProfilePage from "@/pages/MyProfilePage";
import { loader as myProfilePageLoader } from "@/pages/MyProfilePage/controller";
import MyTransactionsPage from "@/pages/MyTransactionsPage";
import NotFoundPage from "@/pages/NotFoundPage";
import queryClient from "@/query/queryClient";
import GeneralTemplate from "@/templates/GeneralTemplate";

import AssetSettingPage from "./pages/SettingPage/AssetSettingPage";

export default createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route element={<GeneralTemplate />}>
        <Route
          path="/create/outcome"
          element={<CreatePage type={"OUTCOME"} />}
          errorElement={<div>error</div>}
        />
        <Route
          path="/create/income"
          element={<CreatePage type={"INCOME"} />}
          errorElement={<div>error</div>}
        />
        <Route
          path="/create/refund/:id"
          element={<CreatePage type={"REFUND"} />}
          loader={createRefundPageLoader(queryClient)}
          errorElement={<div>error</div>}
        />
        <Route
          path="/detail/:id"
          element={<DetailPage />}
          loader={detailPageLoader(queryClient)}
          errorElement={<div>error</div>}
        />
        <Route path="/mypage" element={<MyPage />} />
        <Route
          path="/mypage/profile"
          element={<MyProfilePage />}
          loader={myProfilePageLoader(queryClient)}
          errorElement={<div>error</div>}
        />
        <Route
          path="/mypage/my-transactions"
          element={<MyTransactionsPage />}
          errorElement={<div>error</div>}
        />
        <Route
          path="/mypage/setting/asset"
          element={<AssetSettingPage />}
          errorElement={<div>error</div>}
        />
        <Route path="/" element={<MainPage />} />
      </Route>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/login/success" element={<LoginRedirectPage />} />
      <Route path="*" element={<NotFoundPage />} />
    </>,
  ),
);
