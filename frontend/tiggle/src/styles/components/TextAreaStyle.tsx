import { Input } from "antd";
import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const TextAreaStyle = styled(Input.TextArea)`
  color: ${({ theme }) => theme.color.bluishGray[600].value};
  border: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};
  font-family: "Pretendard Variable", Pretendard, system-ui, sans-serif;
  border-radius: 4px;
  &.ant-input {
    padding: 8px 12px;
    resize: none;
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
  }
  ${({ theme }) => theme.mq.desktop} {
    &.ant-input {
      padding: 12px 16px;
      ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
    }
  }

  &::placeholder {
    color: ${({ theme }) => theme.color.bluishGray[300].value};
  }
  &:hover {
    border-color: ${({ theme }) => theme.color.bluishGray[400].value};
  }
  &:focus {
    color: ${({ theme }) => theme.color.bluishGray[700].value};
    border-color: ${({ theme }) => theme.color.bluishGray[500].value};
    box-shadow: none;
  }
  &:disabled {
    background-color: ${({ theme }) => theme.color.bluishGray[50].value};
    border-color: ${({ theme }) => theme.color.bluishGray[300].value};
    color: ${({ theme }) => theme.color.bluishGray[400].value};
  }

  &.filled {
    background-color: ${({ theme }) => theme.color.bluishGray[50].value};
    &::placeholder {
      color: ${({ theme }) => theme.color.bluishGray[400].value};
    }
    &:disabled {
      background-color: ${({ theme }) => theme.color.bluishGray[100].value};
      color: ${({ theme }) => theme.color.bluishGray[500].value};
    }
  }
`;
