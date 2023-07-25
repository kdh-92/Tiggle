import { Avatar } from "antd";
import { Bell } from "react-feather";
import { StyledHeaderRight } from "./headerRight.styled";

export default function HeaderRight() {
  return (
    <StyledHeaderRight>
      <Bell className="bell" />
      <Avatar />
    </StyledHeaderRight>
  );
}
