import { StyledHeaderLeft } from "./headerLeft.styled";
import { Menu } from "antd";
import Logo from "../../assets/logo.svg";

export default function HeaderLeft() {
  const item = ["통계", "랭킹"].map((el, index) => ({
    key: String(index + 1),
    label: el,
  }));

  return (
    <StyledHeaderLeft>
      <Logo />
      <Menu mode="horizontal" items={item} />
    </StyledHeaderLeft>
  );
}
