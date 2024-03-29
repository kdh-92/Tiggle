import { forwardRef } from "react";

import { TextAreaProps as AntTextAreaProps } from "antd/lib/input";
import cn from "classnames";

import { TextAreaStyle } from "@/components/atoms/TextArea/TextAreaStyle";
import { isDesktop } from "@/styles/util/screen";

interface TextAreaProps extends Omit<AntTextAreaProps, "variant" | "color"> {
  variant?: "default" | "compact";
  color?: "light" | "toned";
}

const TextArea = forwardRef<HTMLInputElement, TextAreaProps>(
  (
    {
      variant = "default",
      color = "light",
      className,
      ...props
    }: TextAreaProps,
    ref,
  ) => {
    const desktop = isDesktop();
    return (
      <TextAreaStyle
        ref={ref}
        className={cn(className, variant, color)}
        size={desktop ? "large" : "middle"}
        rows={3}
        count={variant !== "compact" ? { show: true, max: 300 } : undefined}
        {...props}
      />
    );
  },
);

export default TextArea;
