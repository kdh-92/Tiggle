import styled from "styled-components";

import { expandTypography } from "../util/expandTypography";

export const MainHeaderStyle = styled.div`
  position: sticky;
  top: 0;
  z-index: 1;
  background: ${({ theme: { color } }) => color.bluishGray[50].value};
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)};
  padding-inline: 0;
  display: flex;
  justify-content: center;

  .header-wrap {
    display: flex;
    justify-content: space-between;
    width: 100%;
    height: 64px;

    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
    }
  }

  .header {
    width: 100%;
    display: flex;
    justify-content: center;
  }

  .header-scroll {
    width: 100%;
    display: flex;
    justify-content: center;
    border-bottom: 1px solid
      ${({ theme: { color } }) => color.bluishGray[300].value};
  }

  .gnb {
    width: inherit;
    display: flex;
    justify-content: space-between;
    padding: 0 24px;

    a {
      display: flex;
      align-items: center;
    }

    ${({ theme }) => theme.mq.desktop} {
      padding: 0 32px;
    }
  }
`;

export const HeaderLeftStyle = styled.div`
  display: grid;
  align-items: center;
  grid-gap: 40px;
  grid-auto-flow: column;

  .left-bar-button {
    display: none;

    > button {
      width: 45px;
      height: 63px;
      background: ${({ theme: { color } }) => color.bluishGray[50].value};
      color: ${({ theme: { color } }) => color.bluishGray[600].value};
    }

    ${({ theme }) => theme.mq.desktop} {
      display: block;
    }
  }
`;

export const HeaderRightStyle = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;

  .right-bar-btn {
    display: flex;
    align-items: center;
    background: ${({ theme: { color } }) => color.bluishGray[50].value};
    border: none;
    padding: 0 4px;
    height: 40px;
    color: ${({ theme: { color } }) => color.bluishGray[600].value};
  }

  .ant-avatar {
    background-color: ${({ theme }) =>
      theme.color.bluishGray[300].value} !important;
  }
`;
