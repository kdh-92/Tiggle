import styled from "styled-components";

import { expandTypography } from "../../../styles/util";

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

  &.error {
    border: 1px solid ${({ theme }) => theme.color.peach[300].value};
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

  .view .remove-btn {
    pointer-events: auto;
  }

  .view .image-item {
    pointer-events: auto;
  }

  .controller {
    color: ${({ theme }) => theme.color.blue[500].value};
    display: flex;
    gap: 24px;
  }

  .upload {
    cursor: pointer;
    min-width: 80px;

    &-filled {
      ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
      display: flex;
      gap: 4px;
      white-space: nowrap;
    }

    &-empty {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 4px;
      white-space: nowrap;
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

    .image-item {
      position: relative;
      display: inline-block;
      margin: 12px;

      &:hover {
        transform: scale(1.02);
        transition: transform 0.2s ease;

        .remove-btn {
          display: flex;
        }

        img {
          opacity: 0.8;
        }
      }

      img {
        width: 80px;
        height: 80px;
        object-fit: cover;
        border-radius: 4px;
        border: 1px solid ${({ theme }) => theme.color.bluishGray[300].value};
        transition: opacity 0.2s ease;
      }
    }

    .remove-btn {
      position: absolute;
      top: -8px;
      right: -8px;
      background: ${({ theme }) => theme.color.peach[500].value};
      color: white;
      border: none;
      border-radius: 50%;
      width: 24px;
      height: 24px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      z-index: 3;
      transition: all 0.2s ease;

      &:hover {
        background: ${({ theme }) => theme.color.peach[600].value};
        transform: scale(1.1);
      }

      &.existing {
        background: ${({ theme }) => theme.color.blue[500].value};

        &:hover {
          background: ${({ theme }) => theme.color.blue[600].value};
        }
      }
    }

    .image-badge {
      position: absolute;
      top: 4px;
      left: 4px;
      background: ${({ theme }) => `${theme.color.bluishGray[900].value}B3`};
      color: white;
      font-size: 10px;
      padding: 2px 6px;
      border-radius: 4px;
      font-weight: 500;
      z-index: 2;

      &.new {
        background: ${({ theme }) => theme.color.green[500].value}E6;
      }
    }

    .edit-mode-info {
      display: flex;
      align-items: center;
      gap: 4px;
      font-size: 12px;
      color: ${({ theme }) => theme.color.bluishGray[600].value};
      margin-top: 8px;

      > div {
        display: flex;
        align-items: center;
        gap: 4px;

        &:not(:first-child) {
          margin-left: 12px;
        }
      }
    }
  }

  @media (max-width: 768px) {
    .remove-btn {
      width: 20px;
      height: 20px;
    }

    .image-badge {
      font-size: 8px;
      padding: 1px 4px;
    }

    .edit-mode-info {
      flex-direction: column;
      align-items: flex-start;
      gap: 4px;

      > div:not(:first-child) {
        margin-left: 0;
      }
    }
  }
`;

export const ErrorMessageStyle = styled.span`
  color: ${({ theme }) => theme.color.peach[500].value};
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
`;
