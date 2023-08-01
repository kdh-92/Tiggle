import HeaderSection from "@/components/molecules/HeaderSection";
import Navigation from "@/components/molecules/Navigation";
import { HeaderStyle } from "@/styles/components/HeaderStyle";

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
