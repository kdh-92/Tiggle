import { styled } from "styled-components";

import { expandTypography } from "@/styles/util";

export const MyTransactionCellStyle = styled.div`
  background-color: ${({ theme: { color } }) => color.white.value};
  padding: 24px;
  color: ${({ theme }) => theme.color.gray[900].value};
  border-bottom: 1px solid
    ${({ theme: { color } }) => color.bluishGray[100].value};

  &:first-child {
    border-radius: 16px 16px 0 0;
  }

  &.OUTCOME:hover {
    background-color: ${({ theme }) => theme.color.blue[50].value};
  }

  &.REFUND:hover {
    background-color: ${({ theme }) => theme.color.blue[50].value};
  }

  &.INCOME:hover {
    background-color: ${({ theme }) => theme.color.green[50].value};
  }

  .tag {
    margin-bottom: 8px;
  }

  .amount {
    display: flex;
    align-items: center;
    gap: 8px;

    &-unit {
      ${({ theme }) => expandTypography(theme.typography.title.small.bold)};
    }

    &.OUTCOME {
      color: ${({ theme }) => theme.color.peach[600].value};
    }

    &.REFUND {
      color: ${({ theme }) => theme.color.blue[600].value};
    }

    &.INCOME {
      color: ${({ theme }) => theme.color.green[600].value};
    }

    ${({ theme }) => theme.mq.desktop} {
      ${({ theme }) => expandTypography(theme.typography.title.medium.bold)};
    }
  }

  .transaction-cell-section {
    padding: 4px 0;

    > .content {
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)};

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)};
      }
    }
  }
`;
