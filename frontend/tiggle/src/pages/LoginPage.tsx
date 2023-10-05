import SocialLoginButton from "@/components/atoms/SocialLoginButton/SocialLoginButton";
import LoginHeader from "@/components/molecules/LoginHeader/LoginHeader";
import { LoginPageStyle } from "@/styles/components/LoginPageStyle";

const LoginPage = () => {
  const handleGoogleLogin = () => {
    window.location.href = process.env.REACT_APP_GOOGLE_REDIRECT_URL;
  };

  return (
    <LoginPageStyle>
      <LoginHeader />
      <SocialLoginButton social_logo={"kakao"} />
      <SocialLoginButton social_logo={"naver"} />
      <SocialLoginButton social_logo={"google"} onClick={handleGoogleLogin} />
    </LoginPageStyle>
  );
};

export default LoginPage;
