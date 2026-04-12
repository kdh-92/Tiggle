import styled from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const CategoryBreakdownWrapper = styled.div`
  width: 100%;
  max-width: 327px;
  box-sizing: border-box;
  background: ${({ theme }) => theme.color.white.value};
  border-radius: 16px;
  padding: 24px;
  margin-bottom: 24px;

  ${({ theme }) => theme.mq.desktop} {
    max-width: 480px;
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

  .category-list {
    display: flex;
    flex-direction: column;
    gap: 14px;
  }

  .category-item {
    display: flex;
    flex-direction: column;
    gap: 6px;

    .category-header {
      display: flex;
      justify-content: space-between;
      align-items: center;

      .category-name {
        ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
        color: ${({ theme }) => theme.color.bluishGray[700].value};

        ${({ theme }) => theme.mq.desktop} {
          ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
        }
      }

      .category-info {
        display: flex;
        align-items: center;
        gap: 8px;

        .category-amount {
          ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
          color: ${({ theme }) => theme.color.bluishGray[800].value};

          ${({ theme }) => theme.mq.desktop} {
            ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
          }
        }

        .category-ratio {
          ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
          color: ${({ theme }) => theme.color.bluishGray[400].value};
        }
      }
    }

    .bar-track {
      width: 100%;
      height: 8px;
      border-radius: 4px;
      background: ${({ theme }) => theme.color.bluishGray[100].value};
      overflow: hidden;

      .bar-fill {
        height: 100%;
        border-radius: 4px;
        background: ${({ theme }) => theme.color.blue[500].value};
        transition: width 0.3s ease;
      }
    }
  }

  .empty-message {
    text-align: center;
    padding: 24px 0;
    ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
    color: ${({ theme }) => theme.color.bluishGray[400].value};
  }
`;
