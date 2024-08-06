import styled, { css } from "styled-components";

import { CTAButtonColors } from "@/components/atoms/CTAButton/CTAButton";
import { expandTypography } from "@/styles/util";

export const CTAButtonStyle = styled.button`
  width: fit-content;
  white-space: nowrap;
  background-color: ${({ theme }) => theme.color.blue[600].value};
  color: ${({ theme }) => theme.color.white.value};
  border-radius: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;

  transition:
    background-color 0.1s ease,
    color 0.1s ease;

  &.sm {
    padding: 0 16px;
    height: 28px;
    ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
  }

  &.md {
    padding: 0 20px;
    height: 32px;
    ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
  }

  &.lg {
    padding: 0 24px;
    height: 40px;
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  }

  &.xl {
    padding: 0 24px;
    height: 48px;
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  }

  &.disabled {
    cursor: not-allowed;
  }

  ${({ theme }) => {
    return CTAButtonColors.map(colorKey => {
      return css`
        &.${colorKey} {
          &.primary {
            background-color: ${theme.color[colorKey][600].value};
            color: ${theme.color.white.value};
            &.disabled {
              background-color: ${theme.color[colorKey][400].value};
            }
          }
          &.secondary {
            background-color: ${theme.color[colorKey][500].value};
            color: ${theme.color.white.value};
            &.disabled {
              background-color: ${theme.color[colorKey][300].value};
            }
          }
          &.light {
            background-color: ${theme.color[colorKey][100].value};
            color: ${theme.color[colorKey][600].value};
            &.disabled {
              color: ${theme.color[colorKey][400].value};
            }
          }
        }
      `;
    });
  }}

  .cta-button-icon {
    width: 16px;
    height: 16px;
  }

  ${({ theme }) => theme.mq.desktop} {
    &.md {
      padding: 0 24px;
      width: 120px;
      height: 40px;
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    }

    &.lg {
      padding: 0 32px;
      width: 148px;
      height: 48px;
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
    }

    &.xl {
      padding: 0 32px;
      width: 180px;
      height: 56px;
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
    }

    .cta-button-icon {
      width: 20px;
      height: 20px;
    }
  }

  &.fullWidth {
    width: 100%;
  }
`;
