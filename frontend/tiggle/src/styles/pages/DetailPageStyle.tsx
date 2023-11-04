import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const DetailPageStyle = styled.div`
  width: 100%;
  max-width: 480px;
  margin: 0 auto;
  padding: 24px 24px 60px;

  section.content {
    margin-bottom: 120px;
    display: flex;
    flex-direction: column;
    gap: 60px;
  }

  section.comment {
  }

  ${({ theme }) => theme.mq.desktop} {
    width: 480px;
    padding: 32px 0 80px;

    section.content {
      gap: 80px;
    }
  }
`;

export const DetailPageContentStyle = styled.div`
  width: 100%;

  .image {
    width: 100%;
    height: 210px;
    background-color: ${({ theme }) => theme.color.bluishGray[100].value};
    border-radius: 12px;
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

  .content {
    padding: 24px;
    border-radius: 12px;
    background-color: ${({ theme }) => theme.color.white.value};
    color: ${({ theme }) => theme.color.bluishGray[700].value};

    display: flex;
    flex-direction: column;
    gap: 24px;

    &-reason {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
    }

    &-tags {
      display: flex;
      flex-flow: row wrap;
      gap: 8px;
    }
  }
`;

export const DetailPageCommentSectionStyle = styled.section`
  .title {
    margin-bottom: 24px;
    display: flex;
    gap: 8px;
    align-items: baseline;

    > .main {
      ${({ theme }) => expandTypography(theme.typography.title.medium.bold)};
      color: ${({ theme }) => theme.color.bluishGray[800].value};
    }
    > .sub {
      ${({ theme }) => expandTypography(theme.typography.body.medium.medium)};
      color: ${({ theme }) => theme.color.bluishGray[500].value};
    }
  }

  .comment-cards {
    display: flex;
    flex-direction: column;
    gap: 16px;
    margin-bottom: 36px;
  }

  ${({ theme }) => theme.mq.desktop} {
    .title {
      > .main {
        ${({ theme }) => expandTypography(theme.typography.title.large.bold)};
      }
      > .sub {
        ${({ theme }) => expandTypography(theme.typography.body.large.medium)};
      }
    }
  }
`;

export const DetailPageReplySectionStyle = styled.div`
  width: 100%;
  background-color: ${({ theme }) => theme.color.bluishGray[50].value};
  border-top: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};

  .title {
    margin-bottom: 24px;
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)};
    color: ${({ theme }) => theme.color.bluishGray[900].value};
  }

  .comments {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  ${({ theme }) => theme.mq.desktop} {
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
