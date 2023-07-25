import styled from "styled-components";
import { Header } from "antd/es/layout/layout";
import HeaderSection from "../molecules/HeaderSection";

const StyledHeader = styled(Header)`
  position: sticky;
  top: 0;
  z-index: 1;
  background: #fff;
  border-bottom: 1px solid #dfe4ec;

  > div {
    display: flex;
    justify-content: center;
  }
`;

export const HeaderOrganism = () => {
  return (
    <StyledHeader>
      <HeaderSection />
    </StyledHeader>
  );
};
