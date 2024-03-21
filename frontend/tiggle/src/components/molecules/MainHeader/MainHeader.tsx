import { Bell } from "react-feather";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";

import { Avatar } from "antd";

import Logo from "@/assets/logo_medium.svg?react";
import { CTAButton } from "@/components/atoms";
import {
  MainHeaderStyle,
  HeaderLeftStyle,
  HeaderRightStyle,
} from "@/components/molecules/MainHeader/MainHeaderStyle";
import useAuth from "@/hooks/useAuth";
import useScroll from "@/hooks/useScroll";
import { RootState } from "@/store";
import { toggleModal } from "@/store/notificationModal";

export default function MainHeader() {
  const { isLogin, profile, requireAuth } = useAuth();
  const { scrolling } = useScroll();
  const dispatch = useDispatch();
  const isModalOpen = useSelector(
    (state: RootState) => state.notificationModal.isOpen,
  );

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
                  <button
                    className={`right-bar-btn ${isModalOpen ? "click" : ""}`}
                    onClick={() => dispatch(toggleModal())}
                  >
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
                  onClick={requireAuth}
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
