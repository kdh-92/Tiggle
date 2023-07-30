import { Bell } from "react-feather";
import { StyledHeaderLeft } from "../../styles/HeaderStyle";
import { StyledHeaderRight } from "../../styles/HeaderStyle";
import Logo from "../../assets/logo.svg";
import { Avatar, Menu } from "antd";

export default function HeaderSection() {
  const item = ["통계", "랭킹"].map((el, index) => ({
    key: String(index + 1),
    label: el,
  }));

  return (
    <div>
      <StyledHeaderLeft>
        <Logo />
        <Menu mode="horizontal" items={item} />
      </StyledHeaderLeft>
      <StyledHeaderRight>
        <Bell className="bell" />
        <Avatar />
      </StyledHeaderRight>
    </div>
  );
}
