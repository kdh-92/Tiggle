import { useNavigate } from "react-router-dom";

import SocialLoginButton from "@/components/atoms/SocialLoginButton/SocialLoginButton";
import LoginHeader from "@/components/molecules/LoginHeader/LoginHeader";
import useLogin from "@/hooks/useAuth";
import useCookie from "@/hooks/useCookie";
import { scrollToTop } from "@/hooks/useScroll";
import { LoginPageStyle } from "@/styles/components/LoginPageStyle";

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

  const { isLogin } = useLogin();

  if (isLogin) {
    navigate("/");
  }

  return (
    <LoginPageStyle>
      <LoginHeader />
      <SocialLoginButton social_logo={"kakao"} />
      <SocialLoginButton social_logo={"naver"} />
      <SocialLoginButton
        social_logo={"google"}
        href={process.env.REACT_APP_GOOGLE_REDIRECT_URL}
        onClick={onSet}
      />
    </LoginPageStyle>
  );
};

export default LoginPage;
