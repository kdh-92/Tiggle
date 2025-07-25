import { ButtonHTMLAttributes } from "react";

import Google from "@/assets/google_logo.svg?react";
import Kakao from "@/assets/kakao_logo.svg?react";
import Naver from "@/assets/naver_logo.svg?react";
import { SocialLoginButtonStyle } from "@/pages/LoginPage/SocialLoginButton/SocialLoginButtonStyle";

interface SocialLoginButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement> {
  social_logo: string;
  href: string;
}

export default function SocialLoginButton({
  social_logo,
  href,
}: SocialLoginButtonProps) {
  return (
    <a href={href} className="login-link">
      <SocialLoginButtonStyle className={social_logo}>
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
    </a>
  );
}
