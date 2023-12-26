import { FormEventHandler, useEffect } from "react";
import { Controller, FormProvider, useForm } from "react-hook-form";

import dayjs from "dayjs";

import withAuth, { AuthProps } from "@/utils/withAuth";

import DateFilter from "./DateFilter/DateFilter";
import ETCFilter from "./ETCFilter/ETCFilter";
import { FilteBoxStyle } from "./MyTransactionsPageStyle";
import TxTypeFilter from "./TxTypeFilter/TxTypeFilter";
import { useTransactionsQueryByMember } from "./query";
import { FilterInputs } from "./types";
import { MypageDetailPageStyle } from "../MyProfilePage/MyDetailPageCommonStyle";

interface MyTransactionPageProps extends AuthProps {}

const MyTransactionsPage = ({ profile }: MyTransactionPageProps) => {
  const method = useForm<FilterInputs>({
    defaultValues: { date: dayjs() },
  });

  const { data: transactionsData } = useTransactionsQueryByMember(profile.id);
  console.log(transactionsData);

  useEffect(() => {
    const subscription = method.watch((value, { name, type }) =>
      console.log(value, name, type),
    );
    return () => subscription.unsubscribe();
  }, [method.watch]);

  const handleSubmit: FormEventHandler<HTMLFormElement> = e => {
    e.preventDefault();
  };

  return (
    <MypageDetailPageStyle>
      <p className="page-title">내 기록 모아보기</p>

      <FormProvider {...method}>
        <FilteBoxStyle onSubmit={handleSubmit}>
          <Controller
            control={method.control}
            name="date"
            render={({ field }) => <DateFilter {...field} />}
          />
          <Controller
            control={method.control}
            name="txType"
            render={({ field }) => <TxTypeFilter {...field} />}
          />
          <ETCFilter />
        </FilteBoxStyle>
      </FormProvider>
    </MypageDetailPageStyle>
  );
};

export default withAuth(MyTransactionsPage);
