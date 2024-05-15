import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import LoadingPage from "@/components/templates/LoadingPage/LoadingPage";
import useAuth from "@/hooks/useAuth";
import { RootState } from "@/store";
import continueUrlStore from "@/store/continueUrl";

const LoginRedirectPage = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { isLogin, isLoginLoading } = useAuth();
  const { url } = useSelector((state: RootState) => state.continueUrl);

  useEffect(() => {
    if (isLoginLoading) return;

    if (isLogin) {
      navigate(url ?? "/");
      dispatch(continueUrlStore.actions.reset());
    } else {
      navigate("/login");
      // messageApi.error("로그인에 실패했습니다. 다시 시도해주세요.");
    }
  }, [isLoginLoading]);

  return <LoadingPage />;
};

export default LoginRedirectPage;
