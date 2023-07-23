import { Breadcrumb, theme, Layout, Menu, Avatar } from "antd";
import { Bell } from "react-feather";
import "./main.css";

const { Header, Content, Footer } = Layout;

const Main = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
    <>
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
        <div className="header-wrap">
          <div className="logo-wrap">
            <p>tiggle</p>
            <Menu
              style={{
                color: "#667BA3",
              }}
              mode="horizontal"
              items={["통계", "랭킹"].map((el, index) => ({
                key: String(index + 1),
                label: el,
              }))}
            />
          </div>
          <div className="user-info">
            <Bell className="bell" />
            <Avatar />
          </div>
        </div>
      </Header>
      <Content
        className="site-layout"
        style={{
          padding: "0 50px",
          display: "flex",
          justifyContent: "center",
        }}
      >
        <div className="content-wrapper">
          <div
            style={{
              padding: 24,
              minHeight: 1000,
              background: colorBgContainer,
            }}
          >
            여기 이제 내용 들어갈 것
          </div>
        </div>
      </Content>
      <Footer
        style={{
          textAlign: "center",
          position: "sticky",
          bottom: 0,
          zIndex: 1,
        }}
      >
        tiggle
      </Footer>
    </>
  );
};

export default Main;
