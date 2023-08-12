import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const CommentCellStyle = styled.div`
  width: 100%;
  padding: 20px;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 8px;
  border: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};

  display: grid;
  grid-template-columns: auto minmax(auto, 100%);
  column-gap: 8px;

  .comment-cell-profile {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.color.bluishGray[300].value};
  }

  .flex-wrapper {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  ${({ theme }) => theme.mq.desktop} {
    width: 704px;
    padding: 24px;
    column-gap: 12px;
  }
`;

export const CommentStyle = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;

  .name {
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    color: ${({ theme }) => theme.color.bluishGray[700].value};
  }

  .date {
    ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
    color: ${({ theme }) => theme.color.bluishGray[400].value}
  }

  .content {
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    color: ${({ theme }) => theme.color.bluishGray[900].value};
  }

  ${({ theme }) => theme.mq.desktop} {
    .name {
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
    }
    .date {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
    }
    .content {
      ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
    }
  }
`;

export const RepliesSectionStyle = styled.div`
  margin-top: 24px;
  display: flex;
  flex-direction: column;
  gap: 24px;

  .reply-cell-divider {
    width: 100%;
    min-height: 1px;
    background-color: ${({ theme }) => theme.color.bluishGray[200].value};
  }

  .reply-cell {
    display: grid;
    grid-template-columns: auto minmax(auto, 100%);
    column-gap: 8px;

    .profile {
      width: 32px;
      height: 32px;
      border-radius: 50%;
      background-color: ${({ theme }) => theme.color.bluishGray[300].value};
    }
    .wrapper {
      display: flex;
      flex-direction: column;
      gap: 8px;
    }
    .name {
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
      color: ${({ theme }) => theme.color.bluishGray[700].value};
    }
    .date {
      ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
      color: ${({ theme }) => theme.color.bluishGray[400].value}
    }
    .content {
      ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
      color: ${({ theme }) => theme.color.bluishGray[900].value};
    }
  }

  .input {
    display: flex;
    flex-direction: column;
    align-items: end;
    gap: 8px;
  }

  ${({ theme }) => theme.mq.desktop} {
    margin-top: 32px;
    gap: 32px;
    .reply-cell {
      .wrapper {
        gap: 12px;
      }
      .name {
        ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
      }
      .date {
        ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
      }
      .content {
        ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
      }
    }
    .input {
      gap: 12px;
    }
  }
`;
