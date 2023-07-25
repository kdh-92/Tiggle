import { Award, Home, PieChart } from "react-feather";
import { StyledFooter } from "./footer.styled";
import FooterIcon from "../atoms/FooterIcon";

export default function Footer() {
  return (
    <StyledFooter>
      <FooterIcon icon={<Home />} iconName="홈" iconClass="focus" />
      <FooterIcon icon={<PieChart />} iconName="통계" />
      <FooterIcon icon={<Award />} iconName="랭킹" />
    </StyledFooter>
  );
}
