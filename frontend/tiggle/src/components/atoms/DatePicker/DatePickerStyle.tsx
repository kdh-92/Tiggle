import { DatePicker } from "antd";
import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const DatePickerStyle = styled(DatePicker)`
  &&& {
    width: 100%;

    .ant-picker-input input {
      font-weight: ${({ theme }) => theme.fontWeights.bold.value};

      &::placeholder {
        font-weight: ${({ theme }) => theme.fontWeights.regular.value};
      }

      &:disabled {
        &::placeholder {
          color: ${({ theme }) => theme.color.bluishGray[500].value};
        }
      }
    }

    &.ant-picker-status-error {
      border-width: 1px;
    }

    .ant-picker-suffix {
      color: ${({ theme }) => theme.color.bluishGray[500].value};
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
