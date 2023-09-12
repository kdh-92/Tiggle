import styled from "styled-components";
import { expandTypography } from "@/styles/util";

export const DetailPageContentStyle = styled.div`
  padding-top: 36px;

  .divider {
    width: 100%;
    height: 1px;
    min-height: 1px;
    background-color: ${({ theme }) => theme.color.bluishGray[200].value};
    margin: 24px 0 60px;
  }

  .content {
    margin-bottom: 60px;
    display: flex;
    flex-direction: column;
    gap: 36px;

    &-image {
      width: 240px;
      height: 180px;
      background-color: ${({ theme }) => theme.color.bluishGray[100].value};
      border: 1px solid ${({ theme }) => theme.color.bluishGray[100].value};
      border-radius: 8px;
      overflow: hidden;
      > img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        overflow: hidden;
        user-select: none;
        -webkit-user-drag: none;
      }
    }

    &-text {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
      color: ${({ theme }) => theme.color.bluishGray[700].value};
    }

    &-tags {
      display: flex;
      flex-flow: row wrap;
      gap: 10px;
    }
  }

  .reaction {
    margin: 0 auto 60px auto;
  }

  ${({ theme }) => theme.mq.desktop} {
    .divider {
      margin: 36px 0 72px;
    }

    .content {
      margin-bottom: 120px;
      gap: 48px;
      &-image {
        width: 360px;
        height: 270px;
      }
      &-text {
        ${({ theme }) => expandTypography(theme.typography.body.large.regular)}
      }
    }

    .reaction {
      margin-bottom: 80px;
    }
  }
`;

export const DetailPageReplySectionStyle = styled.div`
  width: 100%;
  background-color: ${({ theme }) => theme.color.bluishGray[50].value};
  border-top: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};

  .page-container {
    padding-top: 36px;
    padding-bottom: 80px;
  }

  .title {
    margin-bottom: 24px;
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)};
    color: ${({ theme }) => theme.color.bluishGray[900].value};
  }

  .divider {
    width: 100%;
    height: 1px;
    min-height: 1px;
    background-color: ${({ theme }) => theme.color.bluishGray[200].value};
    margin: 36px 0;
  }

  .comments {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  ${({ theme }) => theme.mq.desktop} {
    .page-container {
      padding-top: 48px;
      padding-bottom: 120px;
    }
    .title {
      ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)};
    }
    .divider {
      margin: 48px 0;
    }
    .comments {
      gap: 20px;
    }
  }
`;
