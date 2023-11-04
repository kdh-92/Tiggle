import Banner from "@/components/molecules/Banner/Banner";
import BottomTab from "@/components/molecules/BottomTab/BottomTab";
import TransactionCells from "@/components/molecules/TransactionCell/TransactionCells";
import useGetData from "@/hooks/useGetData";
import { MainPageStyle } from "@/styles/pages/MainPageStyle";

const MainPage = () => {
  const { data } = useGetData();

  return (
    <>
      <MainPageStyle>
        <Banner />
        <TransactionCells data={data} />
        <BottomTab />
      </MainPageStyle>
    </>
  );
};

export default MainPage;
