import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";

import CreatePage from "@/pages/CreatePage";
import DetailPage, { loader as detailPageLoader } from "@/pages/DetailPage";
import LoginPage from "@/pages/LoginPage";
import MainPage from "@/pages/MainPage";
import queryClient from "@/query/queryClient";
import GeneralTemplate from "@/templates/GeneralTemplate";

import NotFoundPage from "./pages/NotFoundPage";

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
          path="/detail/:id"
          element={<DetailPage />}
          loader={detailPageLoader(queryClient)}
          errorElement={<div>error</div>}
        />
        <Route path="/" element={<MainPage />} />
      </Route>
      <Route path="/login" element={<LoginPage />} />
      <Route path="*" element={<NotFoundPage />} />
    </>,
  ),
);
