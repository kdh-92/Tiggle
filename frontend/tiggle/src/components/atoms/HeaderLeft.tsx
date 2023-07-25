import styled from "styled-components";
import { Menu } from "antd";

const StyledHeaderLeft = styled.div`
  display: flex;
  align-items: center;
  width: 50%;
  height: 60px;

  > ul {
    width: 70%;
    border-bottom: none;
    color: #667BA3;
  }
`;

export default function HeaderLeft() {

  const item = ["통계", "랭킹"].map((el, index) => ({
    key: String(index + 1),
    label: el,
  }));
  
  return (
    <StyledHeaderLeft>
        <p>tiggle</p>
        <Menu
          mode="horizontal"
          items={item}
        />
    </StyledHeaderLeft>
  );
};
