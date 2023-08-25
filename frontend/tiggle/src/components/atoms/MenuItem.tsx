import { ReactNode } from "react";

type MenuItem = {
  label: ReactNode;
  key: string;
  classname?: string;
};

type MenuProps = {
  items: MenuItem[];
};

export const MenuItems = (...args: [string, string][]): MenuProps["items"] => {
  return args.map(([customLabel, customKey]) => ({
    label: <span>{customLabel}</span>,
    key: customKey,
  }));
};
