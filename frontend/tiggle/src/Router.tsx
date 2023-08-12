import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";

import DetailPage from "@/pages/DetailPage";
import Main from "@/pages/Main";
import GeneralTemplate from "@/templates/GeneralTemplate";

export default createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route element={<GeneralTemplate />}>
        <Route path="/detail/:id" element={<DetailPage />} />
      </Route>
      <Route path="/" element={<Main />} />
    </>,
  ),
);
