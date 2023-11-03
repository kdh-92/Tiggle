import { Bell } from "react-feather";
import { Link } from "react-router-dom";

import { Avatar } from "antd";

import Logo from "@/assets/logo_medium.svg";
import {
  MainHeaderStyle,
  HeaderLeftStyle,
  HeaderRightStyle,
} from "@/styles/components/MainHeaderStyle";

export default function MainHeader() {
  return (
    <MainHeaderStyle>
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
            <button className="right-bar-btn">
              <Bell size={20} />
            </button>
            <button className="right-bar-btn">
              <Avatar size={24} />
            </button>
          </HeaderRightStyle>
        </div>
      </div>
    </MainHeaderStyle>
  );
}
