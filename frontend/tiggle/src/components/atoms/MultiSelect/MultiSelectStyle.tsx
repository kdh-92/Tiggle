import { Select } from "antd";
import styled from "styled-components";

import { expandTypography } from "../../../styles/util";

export const MultiSelectStyle = styled(Select)`
  &&& {
    width: 100%;

    .ant-select-selector {
      height: 54px;
      padding: 0 18px;
    }

    .ant-select-selection-placeholder {
      font-weight: 400;
      inset-inline-start: unset;
      inset-inline-end: unset;
    }

    .ant-select-selection-search {
      margin-inline-start: unset;
    }

    .ant-select-selection-search-input {
      height: fit-content;
      line-height: inherit;
    }

    .ant-select-arrow {
      color: ${({ theme }) => theme.color.blue[500].value};
      inset-inline-end: 20px;
      height: auto;
      margin-top: unset;
      transform: translateY(-50%);
    }

    .ant-select-selection-item {
      height: 28px;
      padding: 0 8px;
      margin-inline-end: 8px;
      align-items: center;
      border-radius: 8px;
      background-color: ${({ theme }) => theme.color.blue[100].value};
      color: ${({ theme }) => theme.color.blue[600].value};
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    }

    .ant-select-selection-item-remove {
      color: ${({ theme }) => theme.color.blue[400].value};
    }

    // ---- responsive ----
    ${({ theme }) => theme.mq.desktop} {
      width: 480px;

      .ant-select-selector {
        height: 64px;
        padding: 0 20px;
      }

      .ant-select-arrow {
        inset-inline-end: 24px;
      }

      .ant-select-selection-item {
        height: 32px;
        ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
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
  }
`;

export const ErrorMessageStyle = styled.span`
  color: ${({ theme }) => theme.color.peach[500].value};
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
`;
