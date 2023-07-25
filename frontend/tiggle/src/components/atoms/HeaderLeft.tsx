import { StyledHeaderLeft } from "./headerLeft.styled";
import { Menu } from "antd";

export default function HeaderLeft() {
  const item = ["통계", "랭킹"].map((el, index) => ({
    key: String(index + 1),
    label: el,
  }));

  return (
    <StyledHeaderLeft>
      <h2>tiggle</h2>
      <Menu mode="horizontal" items={item} />
    </StyledHeaderLeft>
  );
}
