import BottomTab from "@/components/molecules/BottomTab/BottomTab";
import Banner from "@/pages/MainPage/Banner/Banner";
import { MainPageStyle } from "@/pages/MainPage/MainPageStyle";
import TransactionCells from "@/pages/MainPage/TransactionCell/TransactionCells";

import { useAllTransactionsQuery } from "./query";

const MainPage = () => {
  const { data } = useAllTransactionsQuery();

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
