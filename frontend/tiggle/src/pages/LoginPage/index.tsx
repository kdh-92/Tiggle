import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "@/hooks/useAuth";
import useCookie from "@/hooks/useCookie";
import { scrollToTop } from "@/hooks/useScroll";
import LoginHeader from "@/pages/LoginPage/LoginHeader/LoginHeader";
import { LoginPageStyle } from "@/pages/LoginPage/LoginPageStyle";
import SocialLoginButton from "@/pages/LoginPage/SocialLoginButton/SocialLoginButton";

const LoginPage = () => {
  const navigate = useNavigate();
  scrollToTop();
  const { setCookie } = useCookie();

  const onSet = () => {
    setCookie("key", "value", {
      path: "/",
      secure: true,
      maxAge: 3000,
    });
  };

  const { isLogin } = useAuth();

  useEffect(() => {
    if (isLogin) {
      navigate("/");
    }
  }, [isLogin]);

  return (
    <LoginPageStyle>
      <LoginHeader />
      <SocialLoginButton social_logo={"kakao"} href="#" />
      <SocialLoginButton social_logo={"naver"} href="#" />
      <SocialLoginButton
        social_logo={"google"}
        href={process.env.VITE_GOOGLE_REDIRECT_URL ?? "#"}
        onClick={onSet}
      />
    </LoginPageStyle>
  );
};

export default LoginPage;
