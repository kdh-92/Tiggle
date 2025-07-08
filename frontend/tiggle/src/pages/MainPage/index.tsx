// import BottomTab from "@/components/molecules/BottomTab/BottomTab";
import { useQuery } from "@tanstack/react-query";

import BottomTab from "@/components/molecules/BottomTab/BottomTab";
import { TransactionApiControllerService } from "@/generated";
import Banner from "@/pages/MainPage/Banner/Banner";
import { MainPageStyle } from "@/pages/MainPage/MainPageStyle";
import TransactionCells from "@/pages/MainPage/TransactionCell/TransactionCells";
import { transactionKeys } from "@/query/queryKeys";

const MainPage = () => {
  const { data, isLoading, isError } = useQuery({
    queryKey: transactionKeys.lists(),
    queryFn: () =>
      TransactionApiControllerService.getCountOffsetTransaction(0, 100),
  });

  return (
    <>
      <MainPageStyle>
        <Banner />
        <TransactionCells
          data={data?.data?.transactions}
          isLoading={isLoading}
          isError={isError}
        />
        <BottomTab />
      </MainPageStyle>
    </>
  );
};

export default MainPage;
