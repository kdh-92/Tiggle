import { ButtonHTMLAttributes } from "react";

import { TextButtonStyle } from "@/components/atoms/TextButton/TextButtonStyle";
import { theme } from "@/styles/config/theme";

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const { black, white, ...indexedColor } = theme.color;
type ColorKey = keyof typeof indexedColor;
type ColorIndex = keyof (typeof indexedColor)[keyof typeof indexedColor];
export type Color = "black" | "white" | `${ColorKey}${ColorIndex}`;

interface TextButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  color: Color;
}

const TextButton = ({ color, children, ...props }: TextButtonProps) => {
  return (
    <TextButtonStyle $color={color} {...props}>
      {children}
    </TextButtonStyle>
  );
};

export default TextButton;
