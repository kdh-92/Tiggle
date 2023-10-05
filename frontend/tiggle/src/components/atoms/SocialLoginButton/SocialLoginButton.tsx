import { ButtonHTMLAttributes } from "react";

import Google from "@/assets/google_logo.svg";
import Kakao from "@/assets/kakao_logo.svg";
import Naver from "@/assets/naver_logo.svg";
import { SocialLoginButtonStyle } from "@/styles/components/SocialLoginButton";

interface SocialLoginButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement> {
  social_logo: string;
}

export default function SocialLoginButton({
  social_logo,
  ...props
}: SocialLoginButtonProps) {
  return (
    <SocialLoginButtonStyle className={social_logo} {...props}>
      {social_logo === "kakao" ? (
        <Kakao />
      ) : social_logo === "naver" ? (
        <Naver />
      ) : (
        <Google />
      )}
      <span>
        {social_logo === "kakao"
          ? "카카오 "
          : social_logo === "naver"
          ? "네이버 "
          : "Google "}
        로그인
      </span>
    </SocialLoginButtonStyle>
  );
}
