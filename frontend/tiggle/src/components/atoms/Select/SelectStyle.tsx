import { Select } from "antd";
import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const SelectStyle = styled(Select)`
  &&& {
    width: 100%;

    .ant-select-selector {
      padding: 0 20px;
    }

    .ant-select-selection-item {
      font-weight: 700;
      color: ${({ theme }) => theme.color.blue[600].value};
    }

    .ant-select-selection-placeholder {
      font-weight: 400;
    }

    .ant-select-arrow {
      color: ${({ theme }) => theme.color.blue[500].value};
      inset-inline-end: 20px;
      height: auto;
      margin-top: unset;
      transform: translateY(-50%);
    }

    // ---- responsive ----
    ${({ theme }) => theme.mq.desktop} {
      width: 480px;

      .ant-select-selector {
        padding: 0 24px;
      }

      .ant-select-arrow {
        inset-inline-end: 24px;
      }
    }

    // ---- disabled ----
    &.ant-select-disabled {
      .ant-select-arrow {
        color: ${({ theme }) => theme.color.bluishGray[300].value};
      }

      .ant-select-selection-item {
        color: inherit;
      }
      .ant-select-selection-placeholder {
        color: ${({ theme }) => theme.color.bluishGray[500].value};
      }
    }

    // ---- error ----
    &.ant-select-status-error {
      .ant-select-selector {
        border-width: 1px;
      }
    }

    // ---- variant ----
    &.compact {
      width: fit-content;

      .ant-select-selector {
        display: flex;
        align-items: center;
        padding: 0 12px;
        height: 36px;
        padding-inline-end: calc(12px + 20px);
      }

      .ant-select-selection-item,
      .ant-select-selection-placeholder {
        padding-right: 8px;
      }

      .ant-select-arrow {
        inset-inline-end: 12px;
      }

      ${({ theme }) => theme.mq.desktop} {
        .ant-select-selector {
          padding: 0 16px;
          height: 42px;
          padding-inline-end: calc(16px + 24px);
        }
        .ant-select-arrow {
          inset-inline-end: 16px;
        }
      }
    }
  }
`;

export const ErrorMessageStyle = styled.span`
  color: ${({ theme }) => theme.color.peach[500].value};
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
`;
