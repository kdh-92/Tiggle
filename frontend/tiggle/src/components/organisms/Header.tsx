import { Header } from "antd/es/layout/layout";
import HeaderSection from "../molecules/HeaderSection";

export const HeaderOrganism = () => {
  return (
    <Header
      style={{
        position: "sticky",
        top: 0,
        zIndex: 1,
        width: "100%",
        background: "white",
        justifyContent: "space-between",
        borderBottom: "1px solid #DFE4EC",
      }}
    >
        <HeaderSection />
    </Header>
  );
};

