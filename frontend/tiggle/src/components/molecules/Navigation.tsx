import { MenuItems } from "@/components/atoms/MenuItem";
import { NavigationStyle } from "@/styles/NavigationStyle";

export default function Navigation() {
  const items = MenuItems(["전체", "all"], ["내 지출", "expenses"]);
  return <NavigationStyle mode="horizontal" items={items} />;
}
