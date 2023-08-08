import { InputProps } from "antd";
import cn from "classnames";

import { TextAreaStyle } from "@/styles/components/TextAreaStyle";

interface TextAreaProps extends InputProps {
  variant?: "default" | "filled";
}

export default function TextArea({
  variant = "default",
  className,
  ...props
}: TextAreaProps) {
  return (
    <TextAreaStyle className={cn(className, variant)} rows={3} {...props} />
  );
}
