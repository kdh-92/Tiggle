import { Select } from "antd";
import styled from "styled-components";

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
  }
`;
