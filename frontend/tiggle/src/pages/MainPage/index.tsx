// import BottomTab from "@/components/molecules/BottomTab/BottomTab";
import { useQuery } from "@tanstack/react-query";

import BottomTab from "@/components/molecules/BottomTab/BottomTab";
import { TransactionApiControllerService } from "@/generated";
import Banner from "@/pages/MainPage/Banner/Banner";
import { MainPageStyle } from "@/pages/MainPage/MainPageStyle";
import TransactionCells from "@/pages/MainPage/TransactionCell/TransactionCells";
import { transactionKeys } from "@/query/queryKeys";

const MainPage = () => {
  const { data } = useQuery({
    queryKey: transactionKeys.lists(),
    queryFn: () => TransactionApiControllerService.getAllTransaction(),
  });

  return (
    <>
      <MainPageStyle>
        <Banner />
        <TransactionCells data={data?.data} />
        <BottomTab />
      </MainPageStyle>
    </>
  );
};

export default MainPage;
