import { lazy, Suspense } from "react";
import { createBrowserRouter } from "react-router-dom";

import CreatePage, { createPageLoader } from "@/pages/CreatePage";
import DetailPage, { detailPageLoader } from "@/pages/DetailPage";
import LoginPage from "@/pages/LoginPage";
import LoginRedirectPage from "@/pages/LoginRedirectPage";
import MainPage from "@/pages/MainPage";
import MyPage from "@/pages/MyPage";
import MyProfilePage, { myProfilePageLoader } from "@/pages/MyProfilePage";
import MyTransactionsPage from "@/pages/MyTransactionsPage";
import NotFoundPage from "@/pages/NotFoundPage";
import CategorySettingPage from "@/pages/SettingPage/CategorySettingPage";
import queryClient from "@/query/queryClient";
import GeneralTemplate from "@/templates/GeneralTemplate";

const StatisticsPage = lazy(() => import("@/pages/StatisticsPage"));
const CharacterPage = lazy(() => import("@/pages/CharacterPage"));
const CharacterCatalogPage = lazy(() => import("@/pages/CharacterCatalogPage"));
const InventoryPage = lazy(() => import("@/pages/InventoryPage"));
const AchievementPage = lazy(() => import("@/pages/AchievementPage"));
const ChallengePage = lazy(() => import("@/pages/ChallengePage"));
const ChallengeCreatePage = lazy(() => import("@/pages/ChallengeCreatePage"));
const ChallengeDetailPage = lazy(() => import("@/pages/ChallengeDetailPage"));

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const withSuspense = (Component: React.ComponentType<any>) => (
  <Suspense fallback={<div />}>
    <Component />
  </Suspense>
);

const router = createBrowserRouter([
  {
    path: "",
    element: <GeneralTemplate />,
    errorElement: <NotFoundPage />,
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
        path: "/create",
        element: <CreatePage />,
      },
      {
        path: "/create/edit/:id",
        element: <CreatePage />,
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
      {
        path: "/mypage/setting/category",
        element: <CategorySettingPage />,
      },
      // Gamification routes
      {
        path: "/statistics",
        element: withSuspense(StatisticsPage),
      },
      {
        path: "/mypage/character",
        element: withSuspense(CharacterPage),
      },
      {
        path: "/mypage/character/catalog",
        element: withSuspense(CharacterCatalogPage),
      },
      {
        path: "/mypage/inventory",
        element: withSuspense(InventoryPage),
      },
      {
        path: "/mypage/achievements",
        element: withSuspense(AchievementPage),
      },
      {
        path: "/challenges",
        element: withSuspense(ChallengePage),
      },
      {
        path: "/challenges/create",
        element: withSuspense(ChallengeCreatePage),
      },
      {
        path: "/challenges/:id",
        element: withSuspense(ChallengeDetailPage),
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
  {
    path: "*",
    element: <NotFoundPage />,
  },
]);

export default router;
