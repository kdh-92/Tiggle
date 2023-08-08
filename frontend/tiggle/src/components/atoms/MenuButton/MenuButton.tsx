import { ButtonHTMLAttributes } from "react";
import { MoreVertical } from "react-feather";

import { MenuButtonStyle } from "@/styles/components/MenuButtonStyle";

interface MenuButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {}

export default function MenuButton({ ...props }: MenuButtonProps) {
  return (
    <MenuButtonStyle {...props}>
      <MoreVertical className="menu-icon" />
    </MenuButtonStyle>
  );
}
