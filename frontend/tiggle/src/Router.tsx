import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";

import queryClient from "@/client";
import DetailPage, { loader as detailPageLoader } from "@/pages/DetailPage";
import Main from "@/pages/Main";
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
      </Route>
      <Route path="/" element={<Main />} />
    </>,
  ),
);
