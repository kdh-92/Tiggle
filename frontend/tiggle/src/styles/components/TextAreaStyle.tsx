import { Input } from "antd";
import styled from "styled-components";

import { expandTypography } from "../util";

export const TextAreaStyle = styled(Input.TextArea)`
  &&& {
    width: 100%;
    font-weight: 500;
    resize: none;
    textarea {
      background-color: transparent;
      resize: none;
    }

    .ant-input,
    &.ant-input {
      padding: 16px 20px;
      &::placeholder {
        font-weight: 400;
      }
    }

    &.compact {
      border-radius: 8px;
      .ant-input,
      &.ant-input {
        padding: 8px 12px;
        font-size: ${({ theme }) => theme.fontSize.body.medium.value}px;
      }
    }

    &.toned {
      background-color: ${({ theme }) => theme.color.bluishGray[50].value};
      &.ant-input-disabled {
        background-color: ${({ theme }) => theme.color.bluishGray[100].value};
      }
    }

    &.ant-input-disabled {
      &::placeholder {
        color: ${({ theme }) => theme.color.bluishGray[500].value};
      }
    }

    .ant-input-data-count {
      ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
      color: ${({ theme }) => theme.color.bluishGray[300].value};

      position: absolute;
      right: 0;
      bottom: -4px;
      transform: translateY(100%);
    }

    &.ant-input-out-of-range .ant-input-data-count {
      color: ${({ theme }) => theme.color.peach[500].value};
    }

    ${({ theme }) => theme.mq.desktop} {
      width: 480px;

      .ant-input,
      &.ant-input {
        padding: 20px 24px;
      }

      &.compact {
        width: 100%;
        border-radius: 12px;
        .ant-input,
        &.ant-input {
          padding: 12px 16px;
          font-size: ${({ theme }) => theme.fontSize.body.large.value}px;
        }
      }
    }
  }
`;
