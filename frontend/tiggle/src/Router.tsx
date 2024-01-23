import { createBrowserRouter } from "react-router-dom";

import CreatePage, { createPageLoader } from "@/pages/CreatePage";
import DetailPage, { detailPageLoader } from "@/pages/DetailPage";
import LoginPage from "@/pages/LoginPage";
import LoginRedirectPage from "@/pages/LoginRedirectPage";
import MainPage from "@/pages/MainPage";
import MyPage from "@/pages/MyPage";
import MyProfilePage, { myProfilePageLoader } from "@/pages/MyProfilePage";
import MyTransactionsPage from "@/pages/MyTransactionsPage";
import queryClient from "@/query/queryClient";
import GeneralTemplate from "@/templates/GeneralTemplate";

// TODO: Error element 만들기 & 지정하기
const router = createBrowserRouter([
  {
    path: "",
    element: <GeneralTemplate />,
    children: [
      {
        path: "/",
        element: <MainPage />,
      },
      {
        path: "/detail/:id",
        element: <DetailPage />,
        loader: detailPageLoader(queryClient),
      },
      {
        path: "/create/income", // TODO: optional segment 이용해서 반복 줄이기
        element: <CreatePage type="INCOME" />,
      },
      {
        path: "/create/outcome", // TODO: optional segment 이용해서 반복 줄이기
        element: <CreatePage type="OUTCOME" />,
      },
      {
        path: "/create/refund/:id", // TODO: optional segment 이용해서 반복 줄이기
        element: <CreatePage type="REFUND" />,
        loader: createPageLoader(queryClient),
      },
      {
        path: "/mypage",
        element: <MyPage />,
      },
      {
        path: "/mypage/profile",
        element: <MyProfilePage />,
        loader: myProfilePageLoader(queryClient),
      },
      {
        path: "/mypage/my-transactions",
        element: <MyTransactionsPage />,
      },
    ],
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/login/success",
    element: <LoginRedirectPage />,
  },
]);

export default router;
