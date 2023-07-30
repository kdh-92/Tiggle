import { HeaderStyle } from "../../styles/HeaderStyle";
import HeaderSection from "../molecules/HeaderSection";
import Navigation from "../molecules/Navigation";

export default function Header() {
  return (
    <>
      <HeaderStyle>
        <HeaderSection />
      </HeaderStyle>
      {/* 768px 이하일 때부터 보이는 부분 */}
      <Navigation />
    </>
  );
}
