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
  padding: 0 20px 0 24px;

  .header-wrap {
    display: flex;
    justify-content: space-between;
  }

  ${({ theme }) => theme.mq.desktop} {
    .header-wrap {
      width: 768px;
    }
  }

  ${({ theme }) => theme.mq.mobile} {
    .header-wrap {
      width: 100%;
    }
  }
`;

export const StyledHeaderLeft = styled.div`
  display: grid;
  align-items: center;
  grid-auto-flow: column;
  width: 204px;

  //TODO: ant design 메뉴 CSS 수정
  .ant-menu {
    border-bottom: none;
    color: ${({ theme: { color } }) => color.bluishGray[600].value};

    ${({ theme }) => theme.mq.mobile} {
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
    background: ${({ theme: { color } }) => color.white.value};
    border: none;
    padding: 0 4px;
    height: 40px;
  }
`;
