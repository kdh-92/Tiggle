import { ButtonHTMLAttributes, ReactNode, cloneElement } from "react";

import cn from "classnames";

import { CTAButtonStyle } from "@/styles/components/CTAButtonStyle";

interface CTAButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  size: "md" | "lg";
  icon?: ReactNode;
}

export default function CTAButton({
  size,
  icon,
  className,
  children,
  ...props
}: CTAButtonProps) {
  return (
    <CTAButtonStyle className={cn(className, size)} {...props}>
      {icon && cloneElement(icon, { className: "cta-button-icon" })}
      {children}
    </CTAButtonStyle>
  );
}
