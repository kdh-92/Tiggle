import { Input } from "antd";
import styled from "styled-components";

export const InputStyle = styled(Input)`
  &&& {
    width: 100%;
    font-weight: 700;
    &::placeholder {
      font-weight: 400;
    }

    &.ant-input-disabled {
      &::placeholder {
        color: ${({ theme }) => theme.color.bluishGray[500].value};
      }
    }

    ${({ theme }) => theme.mq.desktop} {
      width: 480px;
    }
  }
`;
