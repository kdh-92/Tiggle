import { Bell } from "react-feather";
import { Link } from "react-router-dom";

import { Avatar } from "antd";

import Logo from "@/assets/logo_medium.svg";
import {
  StyledHeaderLeft,
  StyledHeaderRight,
} from "@/styles/components/HeaderStyle";

export default function HeaderSection() {
  return (
    <div className="header-wrap">
      <div className="gnb">
        <StyledHeaderLeft>
          <Link to={"/"}>
            <Logo />
          </Link>
          <div className="left-bar-button">
            <button>통계</button>
            <button>랭킹</button>
          </div>
        </StyledHeaderLeft>
        <StyledHeaderRight>
          <button className="right-bar-btn">
            <Bell size={20} />
          </button>
          <button className="right-bar-btn">
            <Avatar size={24} />
          </button>
        </StyledHeaderRight>
      </div>
    </div>
  );
}
