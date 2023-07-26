import styled from "styled-components";
import { Header } from "antd/es/layout/layout";

export const StyledHeader = styled(Header)`
  position: sticky;
  top: 0;
  z-index: 1;
  background: #fff;
  border-bottom: 1px solid #dfe4ec;
  padding-inline: 0;

  > div {
    display: flex;
    justify-content: center;
  }
`;
