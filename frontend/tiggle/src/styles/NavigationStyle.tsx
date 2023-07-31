import { Menu } from "antd";
import styled from "styled-components";

export const NavigationStyle = styled(Menu)`
  display: none;
  position: sticky;
  top: 64px;
  left: 0;
  right: 0;
  background-color: #fff;
  border-bottom: 0;
  z-index: 100;
  padding: 0 20px 0 24px;
  height: 52px;
  display: flex;
  align-items: center;
  color: #afbbcf;

  .ant-menu-item {
    padding-inline: 0;
    margin-right: 4px;
    width: 50px;
    text-align: center;
  }

  //TODO: ant design 메뉴 CSS 수정
  .ant-menu-item-selected {
    display: flex;
    width: 55px;
    background-color: #f6f7f9 !important;
    border-radius: 1rem !important;
    height: 30px;
    align-items: center;
    justify-content: center;
    color: #455573 !important;
  }

  //TODO: ant design 메뉴 CSS 수정
  .ant-menu-item-selected::after {
    border-bottom-width: 0 !important;
    border-bottom-color: #fff !important;
  }

  //TODO: ant design 메뉴 CSS 수정
  .ant-menu-item-active::after {
    border-bottom-width: 0 !important;
    border-bottom-color: #fff !important;
  }
`;
