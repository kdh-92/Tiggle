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

    @media (max-width: 768px) {
      display : none;
    }
  }
`;

export default function HeaderLeft() {

  const item = ["통계", "랭킹"].map((el, index) => ({
    key: String(index + 1),
    label: el,
  }));

  return (
    <StyledHeaderLeft>
        <h2>tiggle</h2>
        <Menu
          mode="horizontal"
          items={item}
        />
    </StyledHeaderLeft>
  );
};
