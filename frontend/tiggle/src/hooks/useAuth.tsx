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
    isLoading: isLoginQueryLoading,
    isError: isLoginError,
  } = useQuery(
    memberKeys.detail("me"),
    async () => MemberApiControllerService.getMe(),
    {
      staleTime: 1000 * 60 * 30,
      enabled: !!token,
    },
  );

  // token이 없는 경우, loading 없음
  const isLoginLoading = !!token && isLoginQueryLoading;

  const isLogin = useMemo(() => {
    // token이 없는 경우, false
    if (!token) return false;

    // token이 유효하지 않은 경우
    if (isLoginError) {
      console.log("mari debug - invalid token");
      removeCookie("Authorization");
      queryClient.invalidateQueries(memberKeys.detail("me"));
      // reload page
      navigate(0);
    }

    // token이 있는 경우,
    // query 로딩 종료, error 없음, data 존재하면 true
    return !isLoginLoading && !isLoginError && !!profile;
  }, [token, isLoginLoading, isLoginError, profile]);

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
