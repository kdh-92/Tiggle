import styled, { css } from "styled-components";

import { expandTypography } from "@/styles/util";
import { Tx } from "@/types";
import { convertTxTypeToColor } from "@/utils/txType";

export const TxTypeFilterStyle = styled.div`
  padding: 4px;
  display: flex;
  justify-content: stretch;

  ${({ theme }) => theme.mq.desktop} {
    padding: 6px;
  }
`;

export const TxTypeFilterTabStyle = styled.button`
  height: 40px;
  color: ${({ theme }) => theme.color.bluishGray[500].value};
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  border-radius: 8px;

  display: flex;
  justify-content: center;
  align-items: center;
  flex-grow: 1;

  &.selected {
    background-color: ${({ theme }) => theme.color.white.value};
    ${({ theme }) =>
      Object.values(Tx).map(
        type => css`
          &.${type} {
            color: ${theme.color[convertTxTypeToColor(type)][600].value};
          }
        `,
      )}
  }

  ${({ theme }) => theme.mq.desktop} {
    height: 48px;
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
  }
`;
