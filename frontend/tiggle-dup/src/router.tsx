import { createBrowserRouter } from "react-router-dom";

import MainPage from "./pages/MainPage";
import GeneralTemplate from "./templates/GeneralTemplate";

const router = createBrowserRouter([
  {
    path: "",
    element: <GeneralTemplate />,
    children: [
      {
        path: "/",
        element: <MainPage />,
      },
    ],
  },
]);

export default router;
