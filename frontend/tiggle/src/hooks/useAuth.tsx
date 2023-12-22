import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";

import { useQuery } from "@tanstack/react-query";

import { MemberApiControllerService } from "@/generated";
import { memberKeys } from "@/query/queryKeys";
import continueUrlStore from "@/store/continueUrl";

import useCookie from "./useCookie";

const TEMP_USER_ID = 1;

export default function useAuth() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const { removeCookie } = useCookie();

  const {
    data: profile,
    isLoading: isLoginLoading,
    isError: isLoginError,
  } = useQuery(
    memberKeys.detail("me"),
    async () => MemberApiControllerService.getMe(TEMP_USER_ID),
    {
      staleTime: 1000 * 60 * 30,
    },
  );

  const isLogin = !isLoginLoading && !isLoginError && !!profile;

  const requireAuth = () => {
    dispatch(continueUrlStore.actions.creators.set(location.pathname));
    navigate("/login");
  };

  const logOut = () => {
    removeCookie("Authorization");
    navigate("/login");
  };

  const checkIsLogin = (callback?: () => void) => {
    if (isLogin) {
      callback?.();
    } else {
      requireAuth();
    }
  };

  return {
    isLogin,
    profile,
    requireAuth,
    logOut,
    checkIsLogin,
    isLoginLoading,
  };
}
