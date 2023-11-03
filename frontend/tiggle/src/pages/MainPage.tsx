import { Edit3 } from "react-feather";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import TransactionCells from "@/components/molecules/TransactionCell/TransactionCells";
import Tab from "@/components/organisms/Tab";
import useGetData from "@/hooks/useGetData";
import { MainPageContentStyle } from "@/styles/components/MainPageContentStyle";

const MainPage = () => {
  const { data } = useGetData();

  return (
    <>
      <MainPageContentStyle>
        <div className="content-title-wrap">
          <div className="content-title">
            <p>티끌 모아 태산 ⛰</p>
            <p>지출을 기록하고, 조언을 받아보세요!</p>
            <CTAButton
              size={"lg"}
              icon={<Edit3 />}
              children={"기록하기"}
              className="title-button"
            />
          </div>
        </div>
        <TransactionCells data={data} />
      </MainPageContentStyle>
      <Tab />
    </>
  );
};

export default MainPage;
