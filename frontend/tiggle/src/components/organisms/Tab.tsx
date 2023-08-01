import { Award, Home, PieChart } from "react-feather";

import TabButton from "@/components/atoms/TabButton";
import { TabStyle } from "@/styles/components/TabStyle";

export default function Tab() {
  return (
    <TabStyle>
      <TabButton icon={<Home size={16} />} label="홈" classname="focus" />
      <TabButton icon={<PieChart size={16} />} label="통계" />
      <TabButton icon={<Award size={16} />} label="랭킹" />
    </TabStyle>
  );
}
