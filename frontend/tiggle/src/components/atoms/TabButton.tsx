import { ReactNode } from "react";

type TabButtonProps = {
  icon: ReactNode;
  label: string;
  classname?: string;
};

export default function TabButton({ icon, label, ...props }: TabButtonProps) {
  const { classname } = props;

  return (
    <button className={classname ? `tab-button ${classname}` : "tab-button"}>
      {icon}
      <span>{label}</span>
    </button>
  );
}
