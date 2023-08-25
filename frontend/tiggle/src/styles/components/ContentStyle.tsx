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
    padding: 80px 24px 0 24px;
    ${({ theme }) => expandTypography(theme.typography.title.small.bold)};

    > .title-button {
      margin-top: 24px;

      ${({ theme }) => theme.mq.desktop} {
        margin-left: 24px;
      }
    }

    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
      padding: 80px 32px 0 32px;
      ${({ theme }) => expandTypography(theme.typography.title.medium.bold)};

      > p {
        padding: 0 24px;
      }
    }
  }
`;
