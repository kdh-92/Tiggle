import { HeaderStyle } from "../../styles/HeaderStyle";
import HeaderSection from "../molecules/HeaderSection";
import Navigation from "../molecules/Navigation";

export default function Header() {
  return (
    <>
      <HeaderStyle>
        <HeaderSection />
      </HeaderStyle>
      <Navigation />
    </>
  );
}
