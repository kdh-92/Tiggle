import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const DateFilterStyle = styled.div`
  padding: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .title {
    flex-grow: 1;
    text-align: center;
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
  }

  .move-btn {
    width: 28px;
    height: 28px;
    color: ${({ theme }) => theme.color.bluishGray[600].value};
    display: flex;
    justify-content: center;
    align-items: center;

    > svg {
      width: 20px;
      height: 20px;
    }

    &:disabled {
      color: ${({ theme }) => theme.color.bluishGray[400].value};
    }
  }

  ${({ theme }) => theme.mq.desktop} {
    padding: 12px;

    .title {
      ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)}
    }

    .move-btn {
      width: 32px;
      height: 32px;
      > svg {
        width: 24px;
        height: 24px;
      }
    }
  }
`;
