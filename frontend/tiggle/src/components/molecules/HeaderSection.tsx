import { Bell } from "react-feather";
import { StyledHeaderLeft } from "../../styles/HeaderStyle";
import { StyledHeaderRight } from "../../styles/HeaderStyle";
import Logo from "../../assets/logo.svg";
import { Avatar, Menu } from "antd";
import { MenuItems } from "../atoms/MenuItem";

export default function HeaderSection() {
  const items = MenuItems(["통계", "all"], ["랭킹", "expenses"]);

  return (
    <div>
      <StyledHeaderLeft>
        <Logo />
        <Menu mode="horizontal" items={items} />
      </StyledHeaderLeft>
      <StyledHeaderRight>
        <Bell className="bell" />
        <Avatar />
      </StyledHeaderRight>
    </div>
  );
}
