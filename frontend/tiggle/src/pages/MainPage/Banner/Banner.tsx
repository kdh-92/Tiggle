import { Edit3 } from "react-feather";
import { Link } from "react-router-dom";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import { BannerStyle } from "@/pages/MainPage/Banner/BannerStyle";

export default function Banner() {
  return (
    <BannerStyle>
      <div className="banner-wrap">
        <p className="banner-title">함께 해서 즐거운 절약 생활 🎣</p>
        <p className="banner-sub-title">나의 지출/수입을 공유해보세요</p>
        <Link to="/create">
          <CTAButton
            size={"lg"}
            icon={<Edit3 />}
            children={"기록하기"}
            className="banner-button"
          />
        </Link>
      </div>
    </BannerStyle>
  );
}
