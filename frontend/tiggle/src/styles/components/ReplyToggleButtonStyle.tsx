import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ReplyToggleButtonStyle = styled.button`
  display: flex;
  justify-content: left;
  align-items: center;
  gap: 8px;

  .icon {
    width: 14px;
    height: 14px;
  }
  .count {
    ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  }
  .text {
    ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
  }

  &.outcome {
    .icon,
    .count {
      color: ${({ theme }) => theme.color.peach[400].value};
    }
    .text {
      color: ${({ theme }) => theme.color.peach[500].value};
    }
  }

  &.refund {
    .icon,
    .count {
      color: ${({ theme }) => theme.color.blue[400].value};
    }
    .text {
      color: ${({ theme }) => theme.color.blue[500].value};
    }
  }

  ${({ theme }) => theme.mq.desktop} {
    .icon {
      width: 18px;
      height: 18px;
    }
    .count {
      ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    }
    .text {
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    }
  }
`;
