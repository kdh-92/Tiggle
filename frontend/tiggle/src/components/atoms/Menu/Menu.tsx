import {
  ButtonHTMLAttributes,
  HtmlHTMLAttributes,
  PropsWithChildren,
  useRef,
  useState,
} from "react";
import { MoreVertical } from "react-feather";

import cn from "classnames";

import {
  MenuButtonStyle,
  MenuItemStyle,
  MenuStyle,
} from "@/components/atoms/Menu/MenuStyle";
import useOnClickOutside from "@/hooks/useOnClickOutside";

interface MenuItemProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  label: string;
}

export function MenuItem({ label, ...props }: MenuItemProps) {
  return (
    <MenuItemStyle {...props}>
      <p>{label}</p>
    </MenuItemStyle>
  );
}

export interface MenuProps
  extends PropsWithChildren,
    HtmlHTMLAttributes<HTMLDivElement> {
  align?: "right" | "left" | "center";
  onToggle?: (e?: React.MouseEvent<HTMLButtonElement>) => void;
}

export default function Menu({
  children,
  align = "right",
  onToggle,
  ...props
}: MenuProps) {
  const ref = useRef<HTMLDivElement | null>(null);
  const [open, setOpen] = useState<boolean>(false);

  useOnClickOutside(ref, () => {
    if (open) {
      setOpen(false);
    }
  });

  const handleItemsToggle: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.preventDefault();
    setOpen(!open);
    onToggle?.(e);
  };

  return (
    <MenuStyle ref={ref} $align={align} {...props}>
      <MenuButtonStyle onClick={handleItemsToggle}>
        <MoreVertical className="menu-icon" />
      </MenuButtonStyle>

      <div className={cn("menu-items", { open })}>{children}</div>
    </MenuStyle>
  );
}
