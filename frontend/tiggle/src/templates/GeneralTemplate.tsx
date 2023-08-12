import { Outlet } from "react-router-dom";

import styled from "styled-components";

import Header from "@/components/organisms/Header";

export default function GeneralTemplate() {
  return (
    <GeneralTemplateStyle>
      <Header />
      <div className="container">
        <Outlet />
      </div>
    </GeneralTemplateStyle>
  );
}

const GeneralTemplateStyle = styled.div`
  width: 100%;
  min-height: 100vh;
  background-color: ${({ theme }) => theme.color.white.value};
`;
