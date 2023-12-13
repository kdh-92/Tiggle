import SocialLoginButton from "@/components/atoms/SocialLoginButton/SocialLoginButton";
import LoginHeader from "@/components/molecules/LoginHeader/LoginHeader";
import useCookie from "@/hooks/useCookie";
import { LoginPageStyle } from "@/styles/components/LoginPageStyle";
import withAuth from "@/utils/withAuth";

const LoginPage = () => {
  const { setCookie } = useCookie();

  const onSet = () => {
    setCookie("key", "value", {
      path: "/",
      secure: true,
      maxAge: 3000,
    });
  };

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

export default withAuth(LoginPage, "auth");
