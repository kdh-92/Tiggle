import { useMemo } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";

import continueUrlStore from "@/store/continueUrl";

import useCookie from "./useCookie";

export default function useLogin() {
  const { getCookie, removeCookie } = useCookie();
  const authorization = getCookie("Authorization");

  const isLogin = useMemo(() => !!authorization, [authorization]);

  const logOut = () => {
    removeCookie("Authorization");
  };

  const checkIsLogin = (callback: () => void) => {
    if (isLogin) {
      callback();
    } else {
      const navigate = useNavigate();
      const dispatch = useDispatch();
      const location = useLocation();

      dispatch(continueUrlStore.actions.creators.set(location.pathname));
      navigate("/login");
    }
  };

  return { isLogin, logOut, checkIsLogin };
}
