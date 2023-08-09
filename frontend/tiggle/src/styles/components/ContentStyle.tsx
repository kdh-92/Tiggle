import { Content } from "antd/es/layout/layout";
import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ContentStyle = styled(Content)`
  min-height: 100vh;
  position: relative;

  .content-title-wrap {
    ${({ theme }) => theme.mq.desktop} {
      display: flex;
      justify-content: center;
    }
  }

  .content-title {
    padding: 80px 32px 0 32px;
    ${({ theme }) => expandTypography(theme.typography.title.medium.bold)};

    > .title-button {
      margin-top: 24px;
    }

    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
    }

    ${({ theme }) => theme.mq.mobile} {
      padding: 80px 24px 0 24px;
      ${({ theme }) => expandTypography(theme.typography.title.small.bold)};
    }
  }

  .feed-wrap {
    padding: 0 32px;
    margin-top: 16px;

    ${({ theme }) => theme.mq.desktop} {
      display: flex;
      justify-content: center;
    }

    ${({ theme }) => theme.mq.mobile} {
      padding: 0 24px;
    }
  }

  .feed-box-masonry {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    width: auto;
    gap: 24px;

    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
    }
  }

  .feed {
    background-clip: padding-box;
  }

  .feed > div {
    margin-bottom: 24px;
    border-radius: 24px;
    border: 1px solid ${({ theme: { color } }) => color.gray[200].value};
    background-color: ${({ theme: { color } }) => color.gray[50].value};
    text-align: center;
  }
`;
