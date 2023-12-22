import { useDispatch } from "react-redux";
import { Navigate, useLocation } from "react-router-dom";

import LoadingPage from "@/components/templates/LoadingPage/LoadingPage";
import { MemberDto } from "@/generated";
import useLogin from "@/hooks/useAuth";
import continueUrlStore from "@/store/continueUrl";

export interface AuthProps {
  profile: MemberDto;
}

const LOGIN_PATH = "/login";

const withAuth =
  <Props extends object = AuthProps>(Component: React.ComponentType<Props>) =>
  (props: Omit<Props, keyof AuthProps>) => {
    const dispatch = useDispatch();
    const location = useLocation();

    const { isLogin, isLoginLoading, profile } = useLogin();

    const recordContinueUrl = () =>
      dispatch(continueUrlStore.actions.creators.set(location.pathname));

    if (isLoginLoading) return <LoadingPage />;

    if (!isLogin) {
      recordContinueUrl();
      return <Navigate to={LOGIN_PATH} replace />;
    } else {
      return <Component {...(props as Props)} profile={profile} />;
    }
  };

export default withAuth;
