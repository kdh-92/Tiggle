import styled from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const StatisticsPageWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 0 80px;

  ${({ theme }) => theme.mq.desktop} {
    padding: 60px 0 128px;
  }

  .page-title {
    ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
    color: ${({ theme }) => theme.color.bluishGray[800].value};
    margin-bottom: 32px;

    ${({ theme }) => theme.mq.desktop} {
      ${({ theme }) => expandTypography(theme.typography.title.large.bold)}
      margin-bottom: 40px;
    }
  }

  .summary-cards {
    display: flex;
    gap: 12px;
    width: 327px;
    margin-bottom: 24px;

    ${({ theme }) => theme.mq.desktop} {
      width: 480px;
    }

    .summary-card {
      flex: 1;
      padding: 16px;
      border-radius: 12px;
      background: ${({ theme }) => theme.color.white.value};
      text-align: center;

      .summary-label {
        ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
        color: ${({ theme }) => theme.color.bluishGray[500].value};
        margin-bottom: 8px;
      }

      .summary-amount {
        ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
        color: ${({ theme }) => theme.color.bluishGray[800].value};

        ${({ theme }) => theme.mq.desktop} {
          ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
        }
      }

      &.outcome .summary-amount {
        color: ${({ theme }) => theme.color.peach[600].value};
      }

      &.income .summary-amount {
        color: ${({ theme }) => theme.color.green[600].value};
      }

      &.refund .summary-amount {
        color: ${({ theme }) => theme.color.blue[600].value};
      }
    }
  }

  .section-divider {
    width: 327px;
    height: 1px;
    background: ${({ theme }) => theme.color.bluishGray[100].value};
    margin: 8px 0 24px;

    ${({ theme }) => theme.mq.desktop} {
      width: 480px;
    }
  }

  .loading-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 200px;
  }
`;
