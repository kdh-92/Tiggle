import { forwardRef } from "react";

import { TextAreaProps as AntTextAreaProps } from "antd/lib/input";
import cn from "classnames";

import {
  TextAreaStyle,
  ErrorMessageStyle,
} from "@/components/atoms/TextArea/TextAreaStyle";
import { isDesktop } from "@/styles/util/screen";

interface TextAreaProps extends Omit<AntTextAreaProps, "variant" | "color"> {
  variant?: "default" | "compact";
  color?: "light" | "toned";
  error?: {
    type: string;
    message?: string;
  };
}

const TextArea = forwardRef<HTMLInputElement, TextAreaProps>(
  (
    {
      variant = "default",
      color = "light",
      className,
      error,
      ...props
    }: TextAreaProps,
    ref,
  ) => {
    const desktop = isDesktop();

    return (
      <>
        <TextAreaStyle
          ref={ref}
          className={cn(className, variant, color)}
          size={desktop ? "large" : "middle"}
          rows={3}
          count={variant !== "compact" ? { show: true, max: 300 } : undefined}
          status={error ? "error" : undefined}
          {...props}
        />
        {error && (
          <ErrorMessageStyle>{error.message ?? error.type}</ErrorMessageStyle>
        )}
      </>
    );
  },
);

export default TextArea;
