import { ButtonHTMLAttributes } from "react";
import { Link } from "react-router-dom";

import { ReactComponent as Google } from "@/assets/google_logo.svg";
import { ReactComponent as Kakao } from "@/assets/kakao_logo.svg";
import { ReactComponent as Naver } from "@/assets/naver_logo.svg";
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
    <Link to={href} className="login-link">
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
    </Link>
  );
}
