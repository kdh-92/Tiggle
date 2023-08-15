import { Header } from "antd/es/layout/layout";
import styled from "styled-components";

export const HeaderStyle = styled(Header)`
  position: sticky;
  top: 0;
  z-index: 1;
  background: ${({ theme: { color } }) => color.white.value};
  border-bottom: 1px solid
    ${({ theme: { color } }) => color.bluishGray[200].value};
  padding-inline: 0;
  display: flex;
  justify-content: center;

  .header-wrap {
    display: flex;
    justify-content: space-between;
    padding: 0 20px 0 24px;
    width: 100%;
    height: 64px;

    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
      padding: 0 28px 0 32px;
    }
  }
`;

export const StyledHeaderLeft = styled.div`
  display: grid;
  align-items: center;
  grid-gap: 40px;
  grid-auto-flow: column;

  .ant-menu {
    display: none;
    width: 120px;
    border-bottom: 1px solid
      ${({ theme: { color } }) => color.bluishGray[200].value};
    color: ${({ theme: { color } }) => color.bluishGray[600].value};

    ${({ theme }) => theme.mq.desktop} {
      display: block;
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
    background: ${({ theme: { color } }) => color.white.value};
    border: none;
    padding: 0 4px;
    height: 40px;
  }
`;
