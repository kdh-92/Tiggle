import { Link } from "react-router-dom";

import { ReactComponent as Logo } from "@/assets/logo_large.svg";
import { LoginHeaderStyle } from "@/pages/LoginPage/LoginHeader/LoginHeaderStyle";

export default function LoginHeader() {
  return (
    <LoginHeaderStyle>
      <div>
        <Link to={"/"}>
          <Logo />
        </Link>
      </div>
      <p className="slogan">
        함께해서 즐거운 <span>절약 생활</span> 🎣
      </p>
    </LoginHeaderStyle>
  );
}
