import { useMemo } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";

import { useQuery } from "@tanstack/react-query";

import { MemberApiControllerService } from "@/generated";
import queryClient from "@/query/queryClient";
import { memberKeys } from "@/query/queryKeys";
import continueUrlStore from "@/store/continueUrl";

import useCookie from "./useCookie";

export default function useAuth() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const { getCookie, removeCookie } = useCookie();
  const token = getCookie("Authorization");

  const {
    data: profile,
    isLoading: isLoginLoading,
    isError: isLoginError,
  } = useQuery(
    memberKeys.detail("me"),
    async () => MemberApiControllerService.getMe(),
    {
      staleTime: 1000 * 60 * 30,
      enabled: !!token,
    },
  );

  const isLogin = useMemo(
    () => !isLoginLoading && !isLoginError && !!profile,
    [isLoginLoading, isLoginError, profile],
  );

  const requireAuth = () => {
    dispatch(continueUrlStore.actions.set(location.pathname));
    navigate("/login");
  };

  const logOut = () => {
    removeCookie("Authorization");
    queryClient.invalidateQueries(memberKeys.detail("me"));
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
