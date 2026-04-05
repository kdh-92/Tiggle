import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "@/hooks/useAuth";
import { useScrollToTop } from "@/hooks/useScroll";
import LoginHeader from "@/pages/LoginPage/LoginHeader/LoginHeader";
import { LoginPageStyle } from "@/pages/LoginPage/LoginPageStyle";
import SocialLoginButton from "@/pages/LoginPage/SocialLoginButton/SocialLoginButton";

const LoginPage = () => {
  const navigate = useNavigate();
  useScrollToTop();

  const { isLogin } = useAuth();

  useEffect(() => {
    if (isLogin) {
      navigate("/");
    }
  }, [isLogin]);

  return (
    <LoginPageStyle>
      <LoginHeader />
      <SocialLoginButton
        social_logo={"kakao"}
        href={import.meta.env.VITE_KAKAO_REDIRECT_URL ?? "#"}
      />
      <SocialLoginButton
        social_logo={"naver"}
        href={import.meta.env.VITE_NAVER_REDIRECT_URL ?? "#"}
      />
      <SocialLoginButton
        social_logo={"google"}
        href={import.meta.env.VITE_GOOGLE_REDIRECT_URL ?? "#"}
      />
    </LoginPageStyle>
  );
};

export default LoginPage;
