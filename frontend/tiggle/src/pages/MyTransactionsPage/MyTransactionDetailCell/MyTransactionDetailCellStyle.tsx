import styled, { css } from "styled-components";

import { expandTypography } from "@/styles/util";
import { Tx } from "@/types";
import { convertTxTypeToColor } from "@/utils/txType";

export const MyTransactionDetailCellStyle = styled.div`
  width: 100%;
  padding: 24px;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 12px;

  display: flex;
  flex-direction: column;
  gap: 24px;

  .header {
    display: flex;
    flex-direction: column;
    gap: 8px;
    align-items: flex-start;

    .amount {
      display: flex;
      gap: 6px;

      ${({ theme }) => expandTypography(theme.typography.title.small.bold)}
      ${({ theme }) =>
        Object.values(Tx).map(
          type => css`
            &.${type} {
              color: ${theme.color[convertTxTypeToColor(type)][500].value};
            }
          `,
        )}
    }
  }

  .body {
    color: ${({ theme }) => theme.color.bluishGray[900].value};

    display: flex;
    flex-direction: column;
    gap: 8px;

    .content {
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
    }

    .reason {
      ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
      opacity: 0.6;
    }
  }

  ${({ theme }) => theme.mq.desktop} {
    border-radius: 16px;
    gap: 32px;

    .header {
      .amount {
        gap: 6px;
        ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
      }
    }

    .body {
      .content {
        ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)}
      }
      .reason {
        ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
      }
    }
  }
`;

export const MyTransactionDetailCellSkeletonStyle = styled.div`
  width: 100%;
  height: 186px;
  padding: 24px;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 12px;

  display: flex;
  flex-direction: column;
  gap: 24px;

  .header {
    display: flex;
    flex-direction: column;
    gap: 8px;
    align-items: flex-start;

    .tag {
      width: 36px;
      height: 18px;
      border-radius: 8px;
      background-color: ${({ theme }) => theme.color.bluishGray[200].value};
    }
    .amount {
      width: 95px;
      height: 26px;
      border-radius: 8px;
      background-color: ${({ theme }) => theme.color.bluishGray[200].value};
    }
  }

  .body {
    display: flex;
    flex-direction: column;
    gap: 8px;

    .content {
      width: 149px;
      height: 22px;
      border-radius: 8px;
      background-color: ${({ theme }) => theme.color.bluishGray[200].value};
    }

    .reason {
      display: flex;
      flex-direction: column;
      gap: 4px;

      &-line {
        height: 14px;
        border-radius: 6px;
        background-color: ${({ theme }) => theme.color.bluishGray[200].value};
        &.line-1 {
          width: 100%;
        }
        &.line-2 {
          width: 201px;
        }
      }
    }
  }

  .animated-skeleton {
    animation: pulse 1.5s infinite;
    &.body {
      animation-delay: 0.2s;
    }
  }

  @keyframes pulse {
    0% {
      opacity: 1;
    }
    50% {
      opacity: 0.5;
    }
    100% {
      opacity: 1;
    }
  }
`;
