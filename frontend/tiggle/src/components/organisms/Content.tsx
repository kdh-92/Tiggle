import styled from "styled-components";
import { Content } from "antd/es/layout/layout";
import ContentSection from "../molecules/ContentSection";

const StyledContent = styled(Content)`
  min-height: 100vh;
  min-width: 100%;
  padding: 1rem;
  display: flex;
  justify-content: center;
`;

export const ContentOrganism = () => {
  return (
    <StyledContent>
      <ContentSection />
    </StyledContent>
  );
};
