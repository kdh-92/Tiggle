import Header from "../components/organisms/Header";
import Footer from "../components/organisms/Footer";
import Content from "../components/organisms/Content";
import { GlobalStyle } from "../styles/GlobalStyle";

const Main = () => {
  return (
    <>
      <GlobalStyle />
      <Header />
      <Content />
      <Footer />
    </>
  );
};

export default Main;
