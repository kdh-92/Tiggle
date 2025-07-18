import { useDispatch } from "react-redux";
import { Navigate, useLocation } from "react-router-dom";

import LoadingPage from "@/components/templates/LoadingPage/LoadingPage";
import { MemberRespDto } from "@/generated";
import useAuth from "@/hooks/useAuth";
import continueUrlStore from "@/store/continueUrl";

export interface AuthProps {
  profile: Required<MemberRespDto>;
}

const LOGIN_PATH = "/login";

const withAuth =
  <Props extends object = AuthProps>(Component: React.ComponentType<Props>) =>
  (props: Omit<Props, keyof AuthProps>) => {
    const dispatch = useDispatch();
    const location = useLocation();

    const { isLogin, isLoginLoading, profile } = useAuth();

    const recordContinueUrl = () =>
      dispatch(continueUrlStore.actions.set(location.pathname));

    if (isLoginLoading) return <LoadingPage />;

    if (!isLogin) {
      recordContinueUrl();
      return <Navigate to={LOGIN_PATH} replace />;
    }

    if (!profile?.data) {
      return <Navigate to={LOGIN_PATH} replace />;
    }

    return <Component {...(props as Props)} profile={profile.data} />;
  };

export default withAuth;
