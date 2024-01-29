import { FormEventHandler, useEffect, useMemo, useRef, useState } from "react";
import { useForm } from "react-hook-form";

import dayjs, { Dayjs } from "dayjs";

import { MemberDto } from "@/generated";

import { useTransactionQueryByFilter } from "./query";
import { FilterInputs } from "./types";

const pageSize = 3;

export const useMyTransactionsPage = (profile: Required<MemberDto>) => {
  const method = useForm<FilterInputs>({ defaultValues: { date: dayjs() } });
  const handleSubmit: FormEventHandler<HTMLFormElement> = e => {
    e.preventDefault();
  };
  const filterWatch = method.watch();

  const { data: filteredTxData } = useTransactionQueryByFilter(profile.id, {
    date: dayjs(filterWatch.date as Dayjs).format(),
    type: filterWatch.txType,
    assetIds: filterWatch.assetIds,
    categoryIds: filterWatch.categoryIds,
    tagNames: filterWatch.tagNames,
  });

  const [index, setIndex] = useState(0);
  const sliceSize = useMemo(() => (index + 1) * pageSize, [index]);
  const isLoadable = useMemo(
    () => filteredTxData && sliceSize < filteredTxData.length,
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
      transactions: filteredTxData?.slice(0, sliceSize),
      isLoadable,
      loaderRef,
    },
  };
};
