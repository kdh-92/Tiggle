import { styled } from "styled-components";

export const LoginHeaderStyle = styled.div`
  margin-top: 160px;

  div {
    margin-bottom: 12px;
    display: flex;
    justify-content: center;
  }

  .slogan {
    margin-bottom: 58px;
    text-align: center;
    color: ${({ theme: { color } }) => color.bluishGray[700].value};

    > span {
      color: ${({ theme: { color } }) => color.blue[600].value};
    }
  }
`;
