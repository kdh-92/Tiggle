import { Input } from "antd";
import styled from "styled-components";

import { expandTypography } from "../../../styles/util";

export const InputStyle = styled(Input)`
  &&& {
    width: 100%;
    font-weight: 700;

    &::placeholder,
    .ant-input::placeholder {
      font-weight: 400;
    }

    &.ant-input-disabled {
      &::placeholder {
        color: ${({ theme }) => theme.color.bluishGray[500].value};
      }
    }

    &.ant-input-status-error {
      border-width: 1px;
    }

    .ant-input-prefix {
      color: ${({ theme }) => theme.color.bluishGray[400].value};
      margin-right: 8px;
      transition: color 0.1s ease;

      &:has(~ .ant-input:focus) {
        color: ${({ theme }) => theme.color.bluishGray[700].value};
      }
    }

    .ant-input-show-count-suffix {
      ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
      color: ${({ theme }) => theme.color.bluishGray[300].value};

      position: absolute;
      right: 0;
      bottom: -4px;
      transform: translateY(100%);
    }

    &.ant-input-out-of-range .ant-input-show-count-suffix {
      color: ${({ theme }) => theme.color.peach[500].value};
    }

    ${({ theme }) => theme.mq.desktop} {
      width: 480px;
    }
  }
`;

export const ErrorMessageStyle = styled.span`
  color: ${({ theme }) => theme.color.peach[500].value};
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
`;
