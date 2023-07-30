import styled from "styled-components";
import { Content } from "antd/es/layout/layout";

export const ContentStyle = styled(Content)`
  min-height: 100vh;
  min-width: 100%;
  padding: 1rem;
  display: flex;
  justify-content: center;
  position: relative;
  top: 50px;

  @media (max-width: 375px) {
    top: 80px;
  }
`;
