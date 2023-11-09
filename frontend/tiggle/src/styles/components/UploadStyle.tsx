import styled from "styled-components";

import { expandTypography } from "../util";

export const UploadStyle = styled.div`
  width: 100%;
  height: 108px;
  padding: 16px;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 12px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 12px;

  &.filled {
    height: 240px;
  }

  input[type="file"] {
    position: absolute;
    width: 0;
    height: 0;
    padding: 0;
    border: 0;
    overflow: hidden;
  }

  .view {
    width: 100%;
    height: auto;
    background-color: ${({ theme }) => theme.color.bluishGray[200].value};
    border-radius: 12px;
    overflow: hidden;
    pointer-events: none;

    > img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .controller {
    color: ${({ theme }) => theme.color.blue[500].value};
    display: flex;
    gap: 24px;
  }

  .upload {
    cursor: pointer;

    &-filled {
      ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
      display: flex;
      gap: 4px;
    }

    &-empty {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 4px;
    }
  }

  .reset {
    ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
    color: inherit;
    display: flex;
    gap: 2px;
  }

  ${({ theme }) => theme.mq.desktop} {
    width: 480px;
    height: 180px;
    border-radius: 16px;
    gap: 16px;

    &.filled {
      height: 300px;
    }

    .view {
      width: 360px;
      border-radius: 16px;
    }

    .controller {
      gap: 32px;
    }

    .upload {
      &-filled {
        ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
      }

      &-empty {
        ${({ theme }) => expandTypography(theme.typography.body.large.regular)}
        gap: 4px;
      }
    }

    .reset {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
    }
  }
`;
