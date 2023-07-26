import { StyledNavigation } from "./navigation.styled";

type MenuItem = {
  label: JSX.Element;
  key: string;
};

type MenuProps = {
  items: MenuItem[];
};

export default function Navigation() {
  const menuItems: MenuProps["items"] = [
    {
      label: <span className="focus">전체</span>,
      key: "all",
    },
    {
      label: <span>내 지출</span>,
      key: "expenses",
    },
  ];
  return <StyledNavigation mode="horizontal" items={menuItems} />;
}
