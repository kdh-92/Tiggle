import { useState } from "react";
import { Award, Home, PieChart } from "react-feather";

import { BottomTabStyle } from "@/components/molecules/BottomTab/BottomTabStyle";

export default function BottomTab() {
  const [isFocus, setIsFocus] = useState<string>("home");

  const handleClickTabButton = (currentTab: string) => {
    setIsFocus(currentTab);
  };

  return (
    <BottomTabStyle>
      <button
        className={isFocus === "home" ? `tab-button focus` : "tab-button"}
        onClick={() => handleClickTabButton("home")}
      >
        <Home size={16} />
        <span>홈</span>
      </button>
      <button
        className={isFocus === "chart" ? `tab-button focus` : "tab-button"}
        onClick={() => handleClickTabButton("chart")}
      >
        <PieChart size={16} />
        <span>통계</span>
      </button>
      <button
        className={isFocus === "award" ? `tab-button focus` : "tab-button"}
        onClick={() => handleClickTabButton("award")}
      >
        <Award size={16} />
        <span>랭킹</span>
      </button>
    </BottomTabStyle>
  );
}
