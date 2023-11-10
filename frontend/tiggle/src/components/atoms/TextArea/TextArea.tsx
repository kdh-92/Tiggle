import { forwardRef } from "react";

import { TextAreaProps as AntTextAreaProps } from "antd/lib/input";
import cn from "classnames";

import { TextAreaStyle } from "@/styles/components/TextAreaStyle";
import { isDesktop } from "@/styles/util/screen";

interface TextAreaProps extends AntTextAreaProps {
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
        count={{ show: true, max: 300 }}
        {...props}
      />
    );
  },
);

export default TextArea;
