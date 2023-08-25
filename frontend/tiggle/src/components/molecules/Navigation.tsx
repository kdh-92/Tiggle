import { MenuItems } from "@/components/atoms/MenuItem";
import {
  NavigationStyle,
  NavigationWrapStyle,
} from "@/styles/components/NavigationStyle";

export default function Navigation() {
  const items = MenuItems(["전체", "all"], ["내 지출", "expenses"]);

  return (
    <NavigationWrapStyle>
      <NavigationStyle
        mode="horizontal"
        items={items}
        defaultSelectedKeys={["all"]}
      />
    </NavigationWrapStyle>
  );
}
