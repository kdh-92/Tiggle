import { Content } from "antd/es/layout/layout"
import ContentSection from "../molecules/ContentSection";
import { ContentProps } from "../../utils/types";

export const ContentOrganism = ({theme}: ContentProps) => {
    return(
        <Content
        className="site-layout"
        style={{
          padding: "0 50px",
          display: "flex",
          justifyContent: "center",
        }}
      >
        <ContentSection theme={theme} />
      </Content>
    );
};
