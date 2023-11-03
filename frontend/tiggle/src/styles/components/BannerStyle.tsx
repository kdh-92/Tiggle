import { styled } from "styled-components";

import { expandTypography } from "../util";

export const BannerStyle = styled.div`
  ${({ theme }) => theme.mq.desktop} {
    display: flex;
    justify-content: center;
  }

  .banner-wrap {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 60px 24px;

    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
      padding: 80px 24px;

      > p {
        padding: 0 24px;
      }
    }

    .banner-title {
      ${({ theme }) => expandTypography(theme.typography.title.small.bold)};

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.title.medium.bold)};
      }
    }

    .banner-sub-title {
      ${({ theme }) => expandTypography(theme.typography.body.medium.medium)};

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.body.large.medium)};
      }
    }

    > .banner-button {
      margin-top: 24px;
    }
  }
`;
