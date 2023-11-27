import { Outlet, useOutletContext } from "react-router-dom";

import { message } from "antd";
import { MessageInstance } from "antd/lib/message/interface";
import styled from "styled-components";

import MainHeader from "@/components/molecules/MainHeader/MainHeader";

type ContextType = { messageApi: MessageInstance };

export default function GeneralTemplate() {
  const [messageApi, contextHolder] = message.useMessage();

  return (
    <>
      {contextHolder}
      <GeneralTemplateStyle>
        <MainHeader />
        <div className="container">
          <Outlet context={{ messageApi } satisfies ContextType} />
        </div>
      </GeneralTemplateStyle>
    </>
  );
}

export const useMessage = () => useOutletContext<ContextType>();

const GeneralTemplateStyle = styled.div`
  width: 100%;
  min-height: 100vh;
  background: ${({ theme: { color } }) => color.bluishGray[50].value};
`;
