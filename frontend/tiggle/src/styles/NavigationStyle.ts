import { Menu } from "antd";
import styled from "styled-components";

export const NavigationStyle = styled(Menu)`
  display: none;

  @media (max-width: 375px) {
    position: fixed;
    top: 64px;
    left: 0;
    right: 0;
    background-color: #fff;
    z-index: 100;
    border-bottom: 1px solid #f0f0f0;
    padding-inline: 40px;
    height: 42px;
    display: flex;
    align-items: center;
    color: #afbbcf;

    .focus {
      display: flex;
      width: 55px;
      background-color: #f6f7f9;
      text-align: center;
      border-radius: 1rem;
      height: 30px;
      align-items: center;
      justify-content: center;
      color: #455573;
    }
  }
`;
