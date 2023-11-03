import Banner from "@/components/molecules/Banner/Banner";
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
      </MainPageStyle>
    </>
  );
};

export default MainPage;
