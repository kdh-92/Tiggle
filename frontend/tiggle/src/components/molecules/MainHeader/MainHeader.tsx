import { Bell } from "react-feather";
import { Link } from "react-router-dom";

import { Avatar } from "antd";

import Logo from "@/assets/logo_medium.svg";
import { CTAButton } from "@/components/atoms";
import useLogin from "@/hooks/useLogin";
import useScroll from "@/hooks/useScroll";
import {
  MainHeaderStyle,
  HeaderLeftStyle,
  HeaderRightStyle,
} from "@/styles/components/MainHeaderStyle";

export default function MainHeader() {
  const { isLogin, profile, logIn } = useLogin();
  const { scrolling } = useScroll();

  return (
    <MainHeaderStyle>
      <div className={scrolling ? "header-scroll" : "header"}>
        <div className="header-wrap">
          <div className="gnb">
            <HeaderLeftStyle>
              <Link to={"/"}>
                <Logo />
              </Link>
              <div className="left-bar-button">
                <button>통계</button>
                <button>랭킹</button>
              </div>
            </HeaderLeftStyle>
            <HeaderRightStyle>
              {isLogin ? (
                <>
                  <button className="right-bar-btn">
                    <Bell size={20} />
                  </button>
                  <Link to={"/mypage"} className="right-bar-btn">
                    <Avatar size={24} src={profile?.profileUrl} />
                  </Link>
                </>
              ) : (
                <CTAButton
                  variant="light"
                  color="blue"
                  size="sm"
                  onClick={() => logIn()}
                >
                  로그인
                </CTAButton>
              )}
            </HeaderRightStyle>
          </div>
        </div>
      </div>
    </MainHeaderStyle>
  );
}
