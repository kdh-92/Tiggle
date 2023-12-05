import { useDispatch } from "react-redux";
import { Navigate, useLocation } from "react-router-dom";

import useLogin from "@/hooks/useLogin";
import continueUrlStore from "@/store/continueUrl";

type Role = "protected" | "auth";

const LOGIN_PATH = "/login";

const withAuth =
  <Props extends object>(Component: React.ComponentType<Props>, role: Role) =>
  (props: Props) => {
    const dispatch = useDispatch();
    const location = useLocation();

    const { isLogin } = useLogin();

    const recordContinueUrl = () =>
      dispatch(continueUrlStore.actions.creators.set(location.pathname));

    switch (role) {
      case "protected":
        if (!isLogin) {
          recordContinueUrl();
          return <Navigate to={LOGIN_PATH} replace />;
        } else {
          return <Component {...(props as Props)} />;
        }
      case "auth":
        if (isLogin) {
          return <Navigate to="/" replace />; //로그인 시 로그인 페이지 진입 불가
        } else {
          return <Component {...(props as Props)} />;
        }
    }
  };

export default withAuth;
