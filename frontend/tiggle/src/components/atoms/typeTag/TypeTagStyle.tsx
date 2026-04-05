import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const TypeTagStyle = styled.div`
  width: fit-content;
  display: flex;
  align-items: center;
  border-radius: 50px;
  color: ${({ theme: { color } }) => color.white.value};
  gap: 10px;

  .label {
    ${({ theme }) => expandTypography(theme.typography.body.small2x.bold)}

    ${({ theme }) => theme.mq.desktop} {
      ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
    }
  }

  &.md {
    padding: 2px 8px;
    height: 18px;

    ${({ theme }) => theme.mq.desktop} {
      padding: 2px 10px;
      height: 20px;
    }
  }

  &.lg {
    padding: 2px 10px;
    height: 20px;

    ${({ theme }) => theme.mq.desktop} {
      padding: 2px 12px;
      height: 24px;
    }
  }

  &.OUTCOME {
    background: ${({ theme: { color } }) => color.blue[500].value};
  }

  &.REFUND {
    background: ${({ theme: { color } }) => color.blue[500].value};
  }

  &.INCOME {
    background: ${({ theme: { color } }) => color.green[500].value};
  }
`;
