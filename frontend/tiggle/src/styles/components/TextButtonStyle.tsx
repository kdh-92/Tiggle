import styled, { css } from "styled-components";

import { Color } from "@/components/atoms/TextButton/TextButton";

import { expandTypography } from "../util";

export const TextButtonStyle = styled.button<{ $color: Color }>`
  min-height: 40px;

  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}

  ${({ $color, theme }) => {
    const [, key, index] = /([a-zA-Z]+)(\d+)/.exec($color);
    if (key === "black") {
      return css`
        color: ${theme.color[key].value};
        &:hover {
          color: ${theme.color.gray[800].value};
        }
      `;
    }
    if (key === "white") {
      return css`
        color: ${theme.color[key].value};
        &:hover {
          color: ${theme.color.gray[50].value};
        }
      `;
    }

    const nIndex = Number(index);
    return css`
      color: ${theme.color[key][index].value};
      &:active {
        color: ${nIndex === 900
          ? theme.color.black.value
          : nIndex === 50
          ? theme.color[key][100].value
          : theme.color[key][nIndex + 100].value};
      }
    `;
  }}

  transition: color 0.1s ease;

  ${({ theme }) => theme.mq.desktop} {
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
  }
`;
