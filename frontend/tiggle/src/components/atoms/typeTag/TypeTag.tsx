import { HTMLAttributes } from "react";

import cn from "classnames";

import { TypeTagStyle } from "@/styles/components/TypeTagStyle";
import { Tx, TxType } from "@/types";

interface TypeTagProps extends HTMLAttributes<HTMLDivElement> {
  size: "md" | "lg";
  txType: TxType;
}

export default function TypeTag({
  size,
  txType,
  className,
  ...props
}: TypeTagProps) {
  return (
    <TypeTagStyle
      className={cn("type-tag", txType, className, size)}
      {...props}
    >
      <p className="label">
        {txType === Tx.OUTCOME
          ? "지출"
          : txType === Tx.REFUND
          ? "환불"
          : "수익"}
      </p>
    </TypeTagStyle>
  );
}
