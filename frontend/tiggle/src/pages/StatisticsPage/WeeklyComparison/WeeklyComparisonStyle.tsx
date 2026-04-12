import styled from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const WeeklyComparisonWrapper = styled.div`
  width: 327px;
  background: ${({ theme }) => theme.color.white.value};
  border-radius: 16px;
  padding: 24px;
  margin-bottom: 24px;

  ${({ theme }) => theme.mq.desktop} {
    width: 480px;
    padding: 32px;
  }

  .section-title {
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    color: ${({ theme }) => theme.color.bluishGray[600].value};
    margin-bottom: 20px;

    ${({ theme }) => theme.mq.desktop} {
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
    }
  }

  .comparison-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 12px;
  }

  .week-card {
    flex: 1;
    padding: 16px;
    border-radius: 12px;
    background: ${({ theme }) => theme.color.bluishGray[50].value};
    text-align: center;

    .week-label {
      ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
      color: ${({ theme }) => theme.color.bluishGray[500].value};
      margin-bottom: 8px;
    }

    .week-amount {
      ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)}
      color: ${({ theme }) => theme.color.bluishGray[800].value};

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.title.small.bold)}
      }
    }

    .week-period {
      ${({ theme }) => expandTypography(theme.typography.body.small2x.regular)}
      color: ${({ theme }) => theme.color.bluishGray[400].value};
      margin-top: 4px;
    }
  }

  .change-indicator {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
    margin-top: 16px;
    padding: 10px 16px;
    border-radius: 8px;
    background: ${({ theme }) => theme.color.bluishGray[50].value};

    .change-text {
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
      }
    }

    &.improved {
      background: ${({ theme }) => theme.color.green[50].value};

      .change-text {
        color: ${({ theme }) => theme.color.green[600].value};
      }

      .arrow-icon {
        color: ${({ theme }) => theme.color.green[600].value};
      }
    }

    &.worsened {
      background: ${({ theme }) => theme.color.peach[50].value};

      .change-text {
        color: ${({ theme }) => theme.color.peach[600].value};
      }

      .arrow-icon {
        color: ${({ theme }) => theme.color.peach[600].value};
      }
    }

    &.neutral {
      .change-text {
        color: ${({ theme }) => theme.color.bluishGray[500].value};
      }
    }
  }

  .anomaly-badge {
    display: inline-flex;
    align-items: center;
    gap: 4px;
    margin-top: 12px;
    padding: 6px 12px;
    border-radius: 6px;
    background: ${({ theme }) => theme.color.peach[50].value};
    color: ${({ theme }) => theme.color.peach[600].value};
    ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
  }

  .no-previous {
    margin-top: 12px;
    text-align: center;
    ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
    color: ${({ theme }) => theme.color.bluishGray[400].value};
  }
`;
