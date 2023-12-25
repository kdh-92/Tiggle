import { Footer } from "antd/es/layout/layout";
import styled from "styled-components";

export const BottomTabStyle = styled(Footer)`
  position: sticky;
  bottom: 0;
  z-index: 1;
  text-align: center;
  height: 58px;
  padding: 0;
  display: grid;
  grid-auto-flow: column;
  background: ${({ theme: { color } }) => color.bluishGray[50].value};

  .tab-button {
    display: flex;
    flex-direction: column;
    align-items: center;
    border: none;
    padding: 0 0 4px 0;
    font-size: 9px;
    gap: 4px;
    background: ${({ theme: { color } }) => color.bluishGray[50].value};
    justify-content: center;
    color: ${({ theme: { color } }) => color.bluishGray[500].value};
  }

  .focus {
    color: ${({ theme: { color } }) => color.blue[600].value};
  }

  ${({ theme }) => theme.mq.desktop} {
    display: none;
  }
`;
