import { ReactNode } from "react";

type FooterIconProps = {
  icon: ReactNode;
  iconName: string;
  iconClass?: string;
};

export default function FooterIcon({
  icon,
  iconName,
  iconClass,
}: FooterIconProps) {
  return (
    <button className={iconClass ? `tab-button ${iconClass}` : "tab-button"}>
      {icon}
      <span>{iconName}</span>
    </button>
  );
}
