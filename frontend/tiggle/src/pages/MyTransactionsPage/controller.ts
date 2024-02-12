import { FormEventHandler, useEffect, useMemo, useRef, useState } from "react";
import { useForm } from "react-hook-form";

import { useQuery } from "@tanstack/react-query";
import dayjs from "dayjs";

import { MemberDto, TransactionApiControllerService } from "@/generated";
import { transactionKeys } from "@/query/queryKeys";

import { FilterInputs } from "./types";

const pageSize = 3;

export const useMyTransactionsPage = (profile: Required<MemberDto>) => {
  const method = useForm<FilterInputs>({ defaultValues: { date: dayjs() } });
  const handleSubmit: FormEventHandler<HTMLFormElement> = e => {
    e.preventDefault();
  };
  const filterWatch = method.watch();

  const { data: filteredTxData } = useQuery({
    queryKey: transactionKeys.list({
      memberId: profile.id,
      filter: filterWatch,
    }),
    queryFn: async () =>
      TransactionApiControllerService.getMemberCountOffsetTransaction(
        profile.id,
        0,
        100,
        undefined, // dayjs(filterWatch.date).date(1).format(),
        undefined, // dayjs(filterWatch.date).add(1, "month").date(1).subtract(1, "day").format(),
        filterWatch.txType,
        filterWatch.categoryIds,
        filterWatch.assetIds,
        filterWatch.tagNames,
      ),
  });

  const [index, setIndex] = useState(0);
  const sliceSize = useMemo(() => (index + 1) * pageSize, [index]);
  const isLoadable = useMemo(
    () => filteredTxData?.content && sliceSize < filteredTxData.content.length,
    [filteredTxData, sliceSize],
  );

  const [entry, setEntry] = useState<IntersectionObserverEntry>();
  const loaderRef = useRef<HTMLDivElement | null>(null);
  const updateEntry = ([entry]: IntersectionObserverEntry[]) => {
    setEntry(entry);
  };

  useEffect(() => {
    if (entry?.isIntersecting && isLoadable) {
      console.log("load more!");
      setIndex(index + 1);
    }
  }, [entry, isLoadable]);

  useEffect(() => {
    const node = loaderRef?.current;
    if (!node) return;

    const observerParams = { threshold: 0.5, rootMargin: "0%" };
    const observer = new IntersectionObserver(updateEntry, observerParams);
    observer.observe(node);

    () => observer.disconnect();
  }, [loaderRef?.current, isLoadable]);

  return {
    form: { method, handleSubmit },
    data: {
      transactions: filteredTxData?.content,
      isLoadable,
      loaderRef,
    },
  };
};
