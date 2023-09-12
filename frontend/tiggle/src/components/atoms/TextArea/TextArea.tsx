import { forwardRef } from "react";

import { TextAreaProps as AntTextAreaProps } from "antd/lib/input";
import cn from "classnames";

import { TextAreaStyle } from "@/styles/components/TextAreaStyle";

interface TextAreaProps extends AntTextAreaProps {
  variant?: "default" | "filled";
}

const TextArea = forwardRef<HTMLInputElement, TextAreaProps>(
  ({ variant = "default", className, ...props }: TextAreaProps, ref) => (
    <TextAreaStyle
      ref={ref}
      className={cn(className, variant)}
      rows={3}
      {...props}
    />
  ),
);

export default TextArea;
