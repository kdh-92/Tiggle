// import BottomTab from "@/components/molecules/BottomTab/BottomTab";
import { useSelector } from "react-redux";

import { useQuery } from "@tanstack/react-query";

import BottomTab from "@/components/molecules/BottomTab/BottomTab";
import { TransactionApiControllerService } from "@/generated";
import Banner from "@/pages/MainPage/Banner/Banner";
import { MainPageStyle } from "@/pages/MainPage/MainPageStyle";
import TransactionCells from "@/pages/MainPage/TransactionCell/TransactionCells";
import { transactionKeys } from "@/query/queryKeys";
import { RootState } from "@/store";

const MainPage = () => {
  const { data } = useQuery({
    queryKey: transactionKeys.lists(),
    queryFn: () => TransactionApiControllerService.getAllTransaction(),
  });
  const isModalOpen = useSelector(
    (state: RootState) => state.notificationModal.isOpen,
  );

  return (
    <>
      <MainPageStyle open={isModalOpen}>
        <Banner />
        <TransactionCells data={data!} />
        <BottomTab />
      </MainPageStyle>
    </>
  );
};

export default MainPage;
