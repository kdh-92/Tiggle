import { Footer } from "antd/es/layout/layout";
import styled from "styled-components";

export const TabStyle = styled(Footer)`
  position: sticky;
  bottom: 0;
  z-index: 1;
  text-align: center;
  height: 58px;
  padding: 0;
  display: grid;
  grid-auto-flow: column;
  background: ${({ theme: { color } }) => color.white.value};
  border-top: 1px solid ${({ theme: { color } }) => color.bluishGray[200].value};

  .tab-button {
    display: flex;
    flex-direction: column;
    align-items: center;
    border: none;
    padding: 0 0 4px 0;
    font-size: 9px;
    gap: 4px;
    background: ${({ theme: { color } }) => color.white.value};
    justify-content: center;
    color: ${({ theme: { color } }) => color.bluishGray[400].value};
  }

  .focus {
    color: ${({ theme: { color } }) => color.black.value};
  }

  ${({ theme }) => theme.mq.desktop} {
    display: none;
  }
`;
