import { useMemo } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";

import { useQuery } from "@tanstack/react-query";

import { MemberApiControllerService } from "@/generated";
import continueUrlStore from "@/store/continueUrl";

import useCookie from "./useCookie";

const TEMP_USER_ID = 1;

export default function useLogin() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const { getCookie, removeCookie } = useCookie();

  const authorization = getCookie("Authorization");
  const isLogin = useMemo(() => !!authorization, [authorization]);

  const {
    data: profile,
    isError: isProfileLoading,
    isLoading: isProfileError,
  } = useQuery(
    ["profile"],
    async () => MemberApiControllerService.getMe(TEMP_USER_ID),
    {
      enabled: isLogin,
      staleTime: 1000 * 60 * 10,
    },
  );

  const logIn = () => {
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
      logIn();
    }
  };

  return {
    isLogin,
    profile,
    logIn,
    logOut,
    checkIsLogin,
    isProfileLoading,
    isProfileError,
  };
}
