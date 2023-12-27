import {
  FormEventHandler,
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import { useForm, useWatch } from "react-hook-form";

import dayjs, { Dayjs } from "dayjs";

import { MemberDto, TransactionRespDto } from "@/generated";

import { useTransactionsQueryByMember } from "./query";
import { FilterInputs } from "./types";

const pageSize = 3;

export const useMyTransactionsPage = (profile: MemberDto) => {
  const method = useForm<FilterInputs>({ defaultValues: { date: dayjs() } });
  const handleSubmit: FormEventHandler<HTMLFormElement> = e => {
    e.preventDefault();
  };

  const filterWatch = useWatch({ control: method.control });

  const filter = useCallback(
    (data: TransactionRespDto) => {
      const { date, txType, assetIds, categoryIds, tagNames } = filterWatch;

      const isTxMatched = txType ? data.type === txType : true;
      const isDateMatched = date
        ? dayjs(data.createdAt).isSame(date as Dayjs, "month")
        : true;
      const isAssetMatched =
        assetIds?.length > 0 ? assetIds.includes(data.asset.id) : true;
      const isCategoryMatched =
        categoryIds?.length > 0 ? categoryIds.includes(data.category.id) : true;
      const isTagMatched =
        tagNames?.length > 0
          ? data.txTagNames?.split(", ").some(tag => tagNames.includes(tag))
          : true;

      return (
        isTxMatched &&
        isDateMatched &&
        isAssetMatched &&
        isCategoryMatched &&
        isTagMatched
      );
    },
    [filterWatch],
  );

  // TODO filtering 적용하기
  // AllTx -> FilteredTx -> SlicedTx
  const { data: transactionsData } = useTransactionsQueryByMember(profile.id);
  const [filteredTx, setFilteredTx] = useState<TransactionRespDto[]>([]);
  useEffect(() => {
    if (transactionsData) {
      setFilteredTx(transactionsData.content.filter(filter));
    }
  }, [filter, transactionsData]);

  const [index, setIndex] = useState(0);
  const sliceSize = useMemo(() => (index + 1) * pageSize, [index]);
  const isLoadable = useMemo(
    () => sliceSize < filteredTx.length,
    [filteredTx, sliceSize],
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
      transactions: filteredTx.slice(0, sliceSize),
      isLoadable,
      loaderRef,
    },
  };
};
