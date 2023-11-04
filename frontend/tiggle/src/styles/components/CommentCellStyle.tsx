import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const CommentCellStyle = styled.div`
  width: 100%;
  padding: 24px;
  border-radius: 12px;
  background-color: ${({ theme }) => theme.color.white.value};

  display: flex;
  flex-direction: column;
  gap: 16px;

  .content {
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    color: ${({ theme }) => theme.color.bluishGray[900].value};
  }

  ${({ theme }) => theme.mq.desktop} {
    padding: 28px;
    border-radius: 16px;
    gap: 20px;

    .content {
      ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
    }
  }
`;

export const CommentSenderStyle = styled.div`
  display: flex;
  gap: 8px;
  align-items: center;

  .profile {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.color.bluishGray[300].value};
  }

  .name {
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    color: ${({ theme }) => theme.color.bluishGray[700].value};
  }

  .date {
    ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
    color: ${({ theme }) => theme.color.bluishGray[400].value}
  }

  ${({ theme }) => theme.mq.desktop} {
    gap: 12px;
    .name {
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
    }
    .date {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
    }
  }
`;

export const CommentRepliesStyle = styled.div`
  padding-left: 24px;
  display: flex;
  flex-direction: column;
  gap: 24px;

  .divider {
    width: 100%;
    min-height: 1px;
    background-color: ${({ theme }) => theme.color.bluishGray[100].value};
  }

  ${({ theme }) => theme.mq.desktop} {
    padding-left: 28px;
    gap: 28px;
  }
`;

export const ReplyCellStyle = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  .content {
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    color: ${({ theme }) => theme.color.bluishGray[800].value};
  }

  ${({ theme }) => theme.mq.desktop} {
    .content {
      ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
    }
  }
`;

export const ReplyFormStyle = styled.form`
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: flex-end;
`;
