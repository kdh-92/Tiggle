import { Menu } from "antd";
import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const NavigationStyle = styled(Menu)`
  background-color: ${({ theme: { color } }) => color.white.value};
  border-bottom: 0;
  z-index: 100;
  width: 768px;
  height: 52px;
  display: flex;
  align-items: center;
  color: ${({ theme: { color } }) => color.bluishGray[400].value};

  ${({ theme }) => theme.mq.desktop} {
    padding: 0 24px;
  }

  .ant-menu-item {
    padding: 8px 16px;
    gap: 10px;
    text-align: center;
  }

  .ant-menu-item-selected {
    display: flex;
    background-color: ${({ theme: { color } }) =>
      color.bluishGray[50].value} !important;
    border-radius: 1rem !important;
    height: 36px;
    align-items: center;
    justify-content: center;
    color: ${({ theme: { color } }) => color.bluishGray[700].value} !important;
  }

  .ant-menu-item-selected::after {
    border-bottom-width: 0 !important;
    border-bottom-color: ${({ theme: { color } }) =>
      color.white.value} !important;
  }

  .ant-menu-item-active::after {
    border-bottom-width: 0 !important;
    border-bottom-color: ${({ theme: { color } }) =>
      color.white.value} !important;
  }
`;

export const NavigationWrapStyle = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 80px;
  padding: 0 20px 0 24px;
  ${({ theme }) => expandTypography(theme.typography.body.small.bold)}

  ${({ theme }) => theme.mq.desktop} {
    padding: 0 20px 0 28px;
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  }
`;
