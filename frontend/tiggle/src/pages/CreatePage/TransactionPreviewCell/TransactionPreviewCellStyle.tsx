import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const TransactionPreviewCellStyle = styled.div`
  width: 100%;
  margin-bottom: 24px;
  display: flex;
  flex-direction: column;
  gap: 8px;

  .cell-label {
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    color: ${({ theme }) => theme.color.bluishGray[600].value}
  }

  .cell-container {
    background-color: ${({ theme }) => theme.color.white.value};
    padding: 24px;
    border-radius: 12px;

    display: flex;
    flex-direction: column;
    gap: 24px;
  }

  .cell-contents-wrapper {
    display: flex;
    flex-direction: column;
    gap: 4px;
  }

  p.amount {
    ${({ theme }) => expandTypography(theme.typography.title.small.bold)};
    &.OUTCOME {
      color: ${({ theme }) => theme.color.peach[600].value};
    }
    &.INCOME {
      color: ${({ theme }) => theme.color.green[600].value};
    }
    &.REFUND {
      color: ${({ theme }) => theme.color.blue[600].value};
    }
  }
  p.content {
    color: ${({ theme }) => theme.color.gray[900].value};
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)};
  }
  p.reason {
    color: ${({ theme }) => theme.color.gray[900].value};
    ${({ theme }) => expandTypography(theme.typography.body.small.regular)};
    opacity: 0.6;
  }

  ${({ theme }) => theme.mq.desktop} {
    margin-bottom: 32px;

    .cell-label {
      ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
    }

    .cell-container {
      border-radius: 16px;
      gap: 28px;
    }

    p.amount {
      ${({ theme }) => expandTypography(theme.typography.title.medium.bold)};
    }
    p.content {
      ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)};
    }
    p.reason {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)};
    }
  }
`;
