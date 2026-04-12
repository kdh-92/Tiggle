import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ChallengeDetailPageStyle = styled.div`
  width: 100%;
  max-width: 480px;
  margin: 0 auto;
  padding: 24px;

  .page-title {
    ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
    color: ${({ theme }) => theme.color.bluishGray[800].value};
    margin-bottom: 24px;
  }

  .challenge-info {
    background-color: ${({ theme }) => theme.color.white.value};
    border-radius: 16px;
    padding: 24px;
    margin-bottom: 24px;

    .info-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 16px;

      .info-type {
        ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
        color: ${({ theme }) => theme.color.bluishGray[800].value};
      }
    }

    .info-stats {
      display: flex;
      justify-content: space-around;
      margin-bottom: 16px;

      .stat-item {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 4px;

        .stat-value {
          ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
          color: ${({ theme }) => theme.color.bluishGray[800].value};
        }

        .stat-label {
          ${({ theme }) =>
            expandTypography(theme.typography.body.small.regular)}
          color: ${({ theme }) => theme.color.bluishGray[400].value};
        }
      }
    }

    .info-date {
      ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
      color: ${({ theme }) => theme.color.bluishGray[400].value};
      text-align: center;
    }
  }

  .calendar-section {
    margin-bottom: 24px;

    .calendar-title {
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
      color: ${({ theme }) => theme.color.bluishGray[600].value};
      margin-bottom: 16px;
    }
  }

  .cancel-button {
    width: 100%;
    height: 48px;
    border-radius: 12px;
    border: 1px solid #f25f5f;
    background-color: ${({ theme }) => theme.color.white.value};
    color: #f25f5f;
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    cursor: pointer;
    transition: background-color 0.2s;

    &:hover {
      background-color: #fff5f5;
    }
  }

  .back-button {
    width: 100%;
    height: 48px;
    border-radius: 12px;
    border: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};
    background-color: ${({ theme }) => theme.color.white.value};
    color: ${({ theme }) => theme.color.bluishGray[600].value};
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    cursor: pointer;
    margin-top: 12px;
    transition: background-color 0.2s;

    &:hover {
      background-color: ${({ theme }) => theme.color.bluishGray[50].value};
    }
  }

  .loading {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 200px;
    color: ${({ theme }) => theme.color.bluishGray[400].value};
  }

  ${({ theme }) => theme.mq.desktop} {
    padding: 32px 0;

    .page-title {
      ${({ theme }) => expandTypography(theme.typography.title.large.bold)}
      text-align: center;
      margin-bottom: 40px;
    }
  }
`;
