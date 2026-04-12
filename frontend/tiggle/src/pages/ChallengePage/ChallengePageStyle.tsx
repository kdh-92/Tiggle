import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ChallengePageStyle = styled.div`
  width: 100%;
  max-width: 480px;
  margin: 0 auto;
  padding: 24px;

  .page-title {
    ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
    color: ${({ theme }) => theme.color.bluishGray[800].value};
    margin-bottom: 24px;
  }

  .active-section {
    margin-bottom: 32px;
  }

  .create-button {
    width: 100%;
    height: 48px;
    border-radius: 12px;
    border: none;
    background-color: ${({ theme }) => theme.color.blue[600].value};
    color: ${({ theme }) => theme.color.white.value};
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    cursor: pointer;
    margin-bottom: 40px;
    transition: opacity 0.2s;

    &:hover {
      opacity: 0.9;
    }

    &:disabled {
      background-color: ${({ theme }) => theme.color.bluishGray[300].value};
      cursor: not-allowed;
      opacity: 1;
    }
  }

  .history-section {
    .history-title {
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
      color: ${({ theme }) => theme.color.bluishGray[600].value};
      margin-bottom: 16px;
    }

    .history-list {
      display: flex;
      flex-direction: column;
      gap: 12px;
    }

    .empty-history {
      text-align: center;
      padding: 40px 0;
      color: ${({ theme }) => theme.color.bluishGray[400].value};
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
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
