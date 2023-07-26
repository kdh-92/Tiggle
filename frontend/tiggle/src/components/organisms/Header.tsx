import { StyledHeader } from "./header.styled";
import HeaderSection from "../molecules/HeaderSection";
import "../../assets/base.css";
import Navigation from "../molecules/Navigation";

export default function Header() {
  return (
    <>
      <StyledHeader>
        <HeaderSection />
      </StyledHeader>
      {/* 375px일 때부터 보이는 부분 */}
      <Navigation />
    </>
  );
}
