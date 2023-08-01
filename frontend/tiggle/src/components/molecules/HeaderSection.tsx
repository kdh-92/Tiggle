import { Avatar, Menu } from "antd";
import { Bell } from "react-feather";

import Logo from "@/assets/logo.svg";
import { MenuItems } from "@/components/atoms/MenuItem";
import {
  StyledHeaderLeft,
  StyledHeaderRight,
} from "@/styles/components/HeaderStyle";

export default function HeaderSection() {
  const items = MenuItems(["통계", "all"], ["랭킹", "expenses"]);

  return (
    <>
      <StyledHeaderLeft>
        <Logo />
        <Menu mode="horizontal" items={items} />
      </StyledHeaderLeft>
      <StyledHeaderRight>
        <button className="right-bar-btn">
          <Bell size={20} color={"#455573"} />
        </button>
        <button className="right-bar-btn">
          <Avatar size={24} />
        </button>
      </StyledHeaderRight>
    </>
  );
}
