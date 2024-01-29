import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const FilterSelectStyle = styled.div`
  width: fit-content;
  position: relative;
`;

export const FilterSelectButtonStyle = styled.button`
  width: fit-content;
  padding: 8px 12px;
  border-radius: 12px;
  background-color: ${({ theme }) => theme.color.white.value};
  color: ${({ theme }) => theme.color.blue[500].value};

  display: flex;
  align-items: center;
  gap: 8px;

  .placeholder {
    ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
  }

  > svg {
    width: 20px;
    height: 20px;
  }

  &:disabled {
    background-color: ${({ theme }) => theme.color.bluishGray[100].value};
    color: ${({ theme }) => theme.color.bluishGray[500].value};
    cursor: not-allowed;
  }
`;

export const OptionCellStyle = styled.li`
  width: 100%;
  padding: 16px;
  ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
  color: ${({ theme }) => theme.color.bluishGray[700].value};

  display: flex;
  justify-content: space-between;
  align-items: center;

  .popover-option-label {
    flex-grow: 1;
  }

  &:not(:last-child) {
    border-bottom: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};
  }

  &.selected {
    background-color: ${({ theme }) => theme.color.blue[50].value};
    color: ${({ theme }) => theme.color.blue[600].value};
  }

  ${({ theme }) => theme.mq.desktop} {
    padding: 12px 16px;
  }
`;
