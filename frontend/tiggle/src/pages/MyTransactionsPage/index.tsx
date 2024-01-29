import { Controller, FormProvider } from "react-hook-form";

import withAuth, { AuthProps } from "@/utils/withAuth";

import DateFilter from "./DateFilter/DateFilter";
import ETCFilter from "./ETCFilter/ETCFilter";
import MyTransactionDetailCell from "./MyTransactionDetailCell/MyTransactionDetailCell";
import MyTransactionDetailCellSkeleton from "./MyTransactionDetailCell/MyTransactionDetailCellSkeleton";
import {
  FilteBoxStyle,
  MyTransactionCellsStyle,
} from "./MyTransactionsPageStyle";
import TxTypeFilter from "./TxTypeFilter/TxTypeFilter";
import { useMyTransactionsPage } from "./controller";
import { MypageDetailPageStyle } from "../MyProfilePage/MyDetailPageCommonStyle";

interface MyTransactionPageProps extends AuthProps {}

const MyTransactionsPage = ({ profile }: MyTransactionPageProps) => {
  const { form, data } = useMyTransactionsPage(profile);

  return (
    <MypageDetailPageStyle>
      <p className="page-title">내 기록 모아보기</p>

      <FormProvider {...form.method}>
        <FilteBoxStyle onSubmit={form.handleSubmit}>
          <Controller
            control={form.method.control}
            name="date"
            render={({ field }) => <DateFilter {...field} />}
          />
          <Controller
            control={form.method.control}
            name="txType"
            render={({ field }) => <TxTypeFilter {...field} />}
          />
          <ETCFilter />
        </FilteBoxStyle>
      </FormProvider>

      <MyTransactionCellsStyle>
        {data.transactions?.map(data => (
          <MyTransactionDetailCell
            key={`transaction-cell-${data.id}`}
            id={data.id!}
            type={data.type!}
            amount={data.amount!}
            content={data.content!}
            reason={data.reason!}
          />
        ))}
        {data.isLoadable && (
          <MyTransactionDetailCellSkeleton ref={data.loaderRef} />
        )}
      </MyTransactionCellsStyle>
    </MypageDetailPageStyle>
  );
};

export default withAuth(MyTransactionsPage);
