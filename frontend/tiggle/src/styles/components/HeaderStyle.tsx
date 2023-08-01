import { Header } from "antd/es/layout/layout";
import styled from "styled-components";

export const HeaderStyle = styled(Header)`
  position: sticky;
  top: 0;
  z-index: 1;
  background: #fff;
  border-bottom: 1px solid #dfe4ec;
  padding-inline: 0;
  display: flex;
  justify-content: space-between;
  padding: 0 20px 0 24px;
`;

export const StyledHeaderLeft = styled.div`
  display: flex;
  align-items: center;

  //TODO: ant design 메뉴 CSS 수정
  .ant-menu {
    border-bottom: none;
    color: #667ba3;

    @media (max-width: 768px) {
      display: none;
    }
  }
`;

export const StyledHeaderRight = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;

  .right-bar-btn {
    display: flex;
    align-items: center;
    background: #fff;
    border: none;
    padding: 0 4px;
    height: 40px;
  }
`;
