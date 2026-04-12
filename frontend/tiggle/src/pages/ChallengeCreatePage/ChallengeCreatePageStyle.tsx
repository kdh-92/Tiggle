import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ChallengeCreatePageStyle = styled.div`
  width: 100%;
  max-width: 480px;
  margin: 0 auto;
  padding: 24px;

  .page-title {
    ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
    color: ${({ theme }) => theme.color.bluishGray[800].value};
    margin-bottom: 32px;
  }

  .form {
    display: flex;
    flex-direction: column;
    gap: 24px;
  }

  .field {
    display: flex;
    flex-direction: column;
    gap: 8px;

    .field-label {
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
      color: ${({ theme }) => theme.color.bluishGray[600].value};
    }

    .field-select {
      width: 100%;
      height: 48px;
      padding: 0 16px;
      border-radius: 12px;
      border: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};
      background-color: ${({ theme }) => theme.color.white.value};
      color: ${({ theme }) => theme.color.bluishGray[800].value};
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
      cursor: pointer;
      appearance: none;

      &:focus {
        outline: none;
        border-color: ${({ theme }) => theme.color.blue[600].value};
      }
    }

    .field-input {
      width: 100%;
      height: 48px;
      padding: 0 16px;
      border-radius: 12px;
      border: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};
      background-color: ${({ theme }) => theme.color.white.value};
      color: ${({ theme }) => theme.color.bluishGray[800].value};
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}

      &:focus {
        outline: none;
        border-color: ${({ theme }) => theme.color.blue[600].value};
      }
    }
  }

  .date-preview {
    background-color: ${({ theme }) => theme.color.bluishGray[50].value};
    border-radius: 12px;
    padding: 16px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .date-label {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
      color: ${({ theme }) => theme.color.bluishGray[500].value};
    }

    .date-value {
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
      color: ${({ theme }) => theme.color.bluishGray[700].value};
    }
  }

  .submit-button {
    width: 100%;
    height: 48px;
    border-radius: 12px;
    border: none;
    background-color: ${({ theme }) => theme.color.blue[600].value};
    color: ${({ theme }) => theme.color.white.value};
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    cursor: pointer;
    margin-top: 8px;
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

  .cancel-button {
    width: 100%;
    height: 48px;
    border-radius: 12px;
    border: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};
    background-color: ${({ theme }) => theme.color.white.value};
    color: ${({ theme }) => theme.color.bluishGray[600].value};
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    cursor: pointer;
    transition: background-color 0.2s;

    &:hover {
      background-color: ${({ theme }) => theme.color.bluishGray[50].value};
    }
  }

  ${({ theme }) => theme.mq.desktop} {
    padding: 32px 0;

    .page-title {
      ${({ theme }) => expandTypography(theme.typography.title.large.bold)}
      text-align: center;
      margin-bottom: 48px;
    }
  }
`;
