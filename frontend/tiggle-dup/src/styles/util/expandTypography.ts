import { css } from "styled-components";

type Token<T extends string | number> = {
  value: T;
  type: string;
};

interface TypoTokens {
  fontFamily: Token<string>;
  fontWeight: Token<number>;
  lineHeight: Token<number>;
  fontSize: Token<number>;
}

export const expandTypography = ({
  fontFamily,
  fontWeight,
  fontSize,
  lineHeight,
}: TypoTokens) => {
  return css`
    ${fontFamily.value !== "Pretendard" ? `font-family: ${fontFamily}` : ""}
    font-weight: ${fontWeight.value};
    line-height: ${lineHeight.value}px;
    font-size: ${fontSize.value}px;
  `;
};
