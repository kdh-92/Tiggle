import { theme } from "antd";
import { HeaderOrganism as Header } from "../components/organisms/Header";
import { FooterOrganism as Footer } from "../components/organisms/Footer";
import { ContentOrganism as Content } from "../components/organisms/Content";
import "./main.css";

const Main = () => {
   
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
    <>
      <Header />
      <Content theme={colorBgContainer} />
      <Footer />
    </>
  );
};

export default Main;
