import { Content } from "antd/es/layout/layout";
import styled from "styled-components";

export const ContentStyle = styled(Content)`
  min-height: 100vh;
  position: relative;

  .content-title-wrap {
    display: flex;
    justify-content: center;
  }

  .content-title {
    font-weight: ${({ theme: { fontWeights } }) => fontWeights.bold.value};
    font-size: ${({ theme: { fontSize } }) => fontSize.title["medium"].value}px;
    padding: 80px 0 0 32px;

    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
    }
  }

  .feed-wrap {
    display: flex;
    justify-content: center;
    padding: 0 32px 0 32px;
  }

  .feed-box-grid {
    margin-top: 16px;
    display: grid;
    grid-template-columns: 1fr; /* 기본값으로 1개의 열 설정 */
    grid-gap: 24px;

    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
      grid-template-columns: repeat(2, minmax(340px, 1fr)); /* 2개의 열 설정 */
    }
  }

  .feed {
    height: fit-content;
    border-radius: 24px;
    border: 1px solid ${({ theme: { color } }) => color.gray[200].value};
    background-color: ${({ theme: { color } }) => color.gray[50].value};
    text-align: center;
  }
`;
