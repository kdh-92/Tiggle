import { ForwardedRef, forwardRef, useCallback, useState } from "react";
import { ControllerRenderProps } from "react-hook-form";

import cn from "classnames";

import { Tx, TxType } from "@/types";
import { convertTxTypeToWord } from "@/utils/txType";

import { TxTypeFilterStyle, TxTypeFilterTabStyle } from "./TxTypeFilterStyle";

interface TxTypeFilterProps extends Omit<ControllerRenderProps, "ref"> {}

const TxTypeFilter = forwardRef(
  (
    { value, onChange }: TxTypeFilterProps,
    ref: ForwardedRef<HTMLDivElement>,
  ) => {
    const [selected, setSelected] = useState<TxType | undefined>(value);

    const selectTxHandler = useCallback(
      (tx: TxType) => () => {
        if (selected === tx) {
          setSelected(undefined);
          onChange(undefined);
        } else {
          setSelected(tx);
          onChange(tx);
        }
      },
      [selected, onChange],
    );

    return (
      <TxTypeFilterStyle className="filter-item" ref={ref}>
        {Object.values(Tx).map(tx => (
          <TxTypeFilterTabStyle
            key={`txtype-filter-tab-${tx}`}
            className={cn(tx, { selected: selected === tx })}
            onClick={selectTxHandler(tx)}
          >
            {convertTxTypeToWord(tx)}
          </TxTypeFilterTabStyle>
        ))}
      </TxTypeFilterStyle>
    );
  },
);

export default TxTypeFilter;
