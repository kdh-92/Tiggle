import { InputProps } from "antd";
import cn from "classnames";

import { StyledTextArea } from "@/styles/components/StyledTextArea";

interface TextAreaProps extends InputProps {
  variant?: "default" | "filled";
}

export default function TextArea({
  variant = "default",
  className,
  ...props
}: TextAreaProps) {
  return (
    <StyledTextArea className={cn(className, variant)} rows={3} {...props} />
  );
}
