import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { Loading } from "@/components/atoms";
import useLogin from "@/hooks/useAuth";
import { RootState } from "@/store";
import continueUrlStore from "@/store/continueUrl";

const LoginRedirectPage = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { isLogin } = useLogin();
  const { url } = useSelector((state: RootState) => state.continueUrl);

  useEffect(() => {
    if (isLogin) {
      navigate(url ?? "/");
      dispatch(continueUrlStore.actions.creators.reset());
    } else {
      navigate("/login");
      // messageApi.error("로그인에 실패했습니다. 다시 시도해주세요.");
    }
  }, []);

  return (
    <div>
      <Loading />
    </div>
  );
};

export default LoginRedirectPage;
