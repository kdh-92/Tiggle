import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";

import DetailPage, { loader as detailPageLoader } from "@/pages/DetailPage";
import LoginPage from "@/pages/LoginPage";
import Main from "@/pages/Main";
import queryClient from "@/query/queryClient";
import GeneralTemplate from "@/templates/GeneralTemplate";

export default createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route element={<GeneralTemplate />}>
        <Route
          path="/detail/:id"
          element={<DetailPage />}
          loader={detailPageLoader(queryClient)}
          errorElement={<div>error</div>}
        />
        <Route path="/" element={<Main />} />
      </Route>
      <Route path="/login" element={<LoginPage />} />
    </>,
  ),
);
