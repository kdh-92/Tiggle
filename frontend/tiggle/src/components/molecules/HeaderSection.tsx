import { Bell } from "react-feather";

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
          {/* TODO: Link 컴포넌트로 Main 페이지 이동 */}
          <Logo />
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
