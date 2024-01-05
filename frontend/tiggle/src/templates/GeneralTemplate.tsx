import { Outlet } from "react-router-dom";

import styled from "styled-components";

import MainHeader from "@/components/molecules/MainHeader/MainHeader";

export default function GeneralTemplate() {
  return (
    <GeneralTemplateStyle>
      <MainHeader />
      <div className="container">
        <Outlet />
      </div>
    </GeneralTemplateStyle>
  );
}

const GeneralTemplateStyle = styled.div`
  width: 100%;
  min-height: 100vh;
  background: ${({ theme: { color } }) => color.bluishGray[50].value};
`;
