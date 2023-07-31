import styled from "styled-components";
import { Footer } from "antd/es/layout/layout";

export const TabStyle = styled(Footer)`
  position: sticky;
  bottom: 0;
  z-index: 1;
  text-align: center;
  height: 58px;
  padding: 0;

  // TODO: tab 역할 지정
  @media (min-width: 768px) {
    display: none;
  }

  @media (max-width: 768px) {
    display: grid;
    grid-auto-flow: column;
    background: #fff;
    border-top: 1px solid #dfe4ec;

    .tab-button {
      display: flex;
      flex-direction: column;
      align-items: center;
      border: none;
      padding: 0 0 4px 0;
      font-size: 9px;
      gap: 4px;
      background: #fff;
      justify-content: center;
      color: #afbbcf;
    }

    .focus {
      color: #000;
    }
  }
`;
