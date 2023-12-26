import {
  ForwardedRef,
  forwardRef,
  useCallback,
  useMemo,
  useState,
} from "react";
import { ChevronLeft, ChevronRight } from "react-feather";
import { ControllerRenderProps } from "react-hook-form";

import dayjs, { Dayjs } from "dayjs";

import { DateFilterStyle } from "./DateFilterStyle";

interface DateFilterProps extends Omit<ControllerRenderProps, "ref"> {}

const DateFilter = forwardRef(
  ({ value, onChange }: DateFilterProps, ref: ForwardedRef<HTMLDivElement>) => {
    const [date, setDate] = useState<Dayjs>(value);

    const isNextDisabled = useMemo(
      () => dayjs(date).isSame(dayjs(), "month"),
      [date],
    );

    const dateMoveHandler = useCallback(
      (direction: "prev" | "next") => () => {
        const newDate =
          direction === "prev"
            ? dayjs(date).subtract(1, "month")
            : dayjs(date).add(1, "month");
        setDate(newDate);
        onChange(newDate);
      },
      [date, onChange],
    );

    return (
      <DateFilterStyle className="filter-item" ref={ref}>
        <button className="move-btn" onClick={dateMoveHandler("prev")}>
          <ChevronLeft />
        </button>
        <p className="title">{dayjs(date).format("YYYY년 MM월")}</p>
        <button
          className="move-btn"
          onClick={dateMoveHandler("next")}
          disabled={isNextDisabled}
        >
          <ChevronRight />
        </button>
      </DateFilterStyle>
    );
  },
);

export default DateFilter;
