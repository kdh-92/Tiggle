import Logo from "@/assets/logo_large.svg";
import { LoginHeaderStyle } from "@/styles/components/LoginHeaderStyle";

export default function LoginHeader() {
  return (
    <LoginHeaderStyle>
      <div>
        {/* TODO: Link 컴포넌트로 Main 페이지 이동 */}
        <Logo />
      </div>
      <p className="slogan">
        함께해서 즐거운 <span>절약 생활</span> 🎣
      </p>
    </LoginHeaderStyle>
  );
}
