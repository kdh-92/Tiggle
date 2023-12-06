import { ButtonHTMLAttributes, ReactElement, cloneElement } from "react";

import cn from "classnames";

import { CTAButtonStyle } from "@/styles/components/CTAButtonStyle";

export const CTAButtonColors = [
  "peach",
  "blue",
  "green",
  "bluishGray",
] as const;
interface CTAButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  size?: "md" | "lg";
  icon?: ReactElement;
  fullWidth?: boolean;
  variant?: "primary" | "secondary" | "light";
  color?: (typeof CTAButtonColors)[number];
}

export default function CTAButton({
  size = "md",
  icon,
  color = "blue",
  fullWidth = false,
  variant = "primary",
  className,
  children,
  ...props
}: CTAButtonProps) {
  return (
    <CTAButtonStyle
      className={cn(className, size, color, variant, { fullWidth })}
      {...props}
    >
      {icon && cloneElement(icon, { className: "cta-button-icon" })}
      {children}
    </CTAButtonStyle>
  );
}
