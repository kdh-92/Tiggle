import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const CTAButtonStyle = styled.button`
  width: fit-content;
  background-color: ${({ theme }) => theme.color.black.value};
  color: ${({ theme }) => theme.color.white.value};
  border-radius: 50px;

  display: flex;
  align-items: center;
  gap: 8px;

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

  .cta-button-icon {
    width: 16px;
    height: 16px;
  }

  ${({ theme }) => theme.mq.desktop} {
    &.md {
      padding: 0 24px;
      height: 40px;
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    }

    &.lg {
      padding: 0 32px;
      height: 48px;
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
    }

    .cta-button-icon {
      width: 20px;
      height: 20px;
    }
  }
`;
