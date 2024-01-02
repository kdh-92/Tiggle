import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ItemStyle = styled.li`
  padding: 12px 16px;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 12px;

  display: flex;
  gap: 16px;

  .label {
    color: ${({ theme }) => theme.color.bluishGray[700].value};
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    flex-grow: 1;
  }

  .controllers {
    > button {
      color: ${({ theme }) => theme.color.blue[500].value};
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
    }

    display: flex;
    gap: 16px;
    flex-shrink: 0;
  }
`;

export const ItemEditInputStyle = styled.input`
  border: none;
  outline: none;

  &::placeholder {
    color: ${({ theme }) => theme.color.bluishGray[400].value};
  }
`;

export const ItemAddStyle = styled.button`
  padding: 12px 16px;
  background-color: ${({ theme }) => theme.color.white.value};
  color: ${({ theme }) => theme.color.blue[500].value};
  border-radius: 12px;
  ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}

  display: flex;
  align-items: center;
  gap: 8px;

  > svg {
    width: 14px;
    height: 14px;
  }
`;
