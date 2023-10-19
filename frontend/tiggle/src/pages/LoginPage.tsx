import SocialLoginButton from "@/components/atoms/SocialLoginButton/SocialLoginButton";
import LoginHeader from "@/components/molecules/LoginHeader/LoginHeader";
import { LoginPageStyle } from "@/styles/components/LoginPageStyle";

const LoginPage = () => {
<<<<<<< HEAD
  const handleGoogleLogin = () => {
    window.location.href = process.env.REACT_APP_GOOGLE_REDIRECT_URL;
  };

=======
>>>>>>> dc62229 (feat: conflict merge)
  return (
    <LoginPageStyle>
      <LoginHeader />
      <SocialLoginButton social_logo={"kakao"} />
      <SocialLoginButton social_logo={"naver"} />
<<<<<<< HEAD
      <SocialLoginButton social_logo={"google"} onClick={handleGoogleLogin} />
=======
      <SocialLoginButton social_logo={"google"} />
>>>>>>> dc62229 (feat: conflict merge)
    </LoginPageStyle>
  );
};

export default LoginPage;
