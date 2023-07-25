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
    <div className={iconClass}>
      {icon}
      <span>{iconName}</span>
    </div>
  );
}
