import styled from "styled-components";

export const LoginPageStyle = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-flow: column;
  align-items: center;
  background-color: ${({ theme: { color } }) => color.bluishGray[50].value};

  a {
    margin-top: 160px;
    margin-bottom: 12px;
  }

  .slogan {
    margin-bottom: 58px;
    color: ${({ theme: { color } }) => color.bluishGray[700].value};

    > span {
      color: ${({ theme: { color } }) => color.blue[600].value};
    }
  }
`;
