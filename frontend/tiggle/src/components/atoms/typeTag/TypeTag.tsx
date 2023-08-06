import { HTMLAttributes } from "react";

import cn from "classnames";

import { TypeTagStyle } from "@/styles/components/TypeTagStyle";
import { TxType } from "@/types";

interface TypeTagProps extends HTMLAttributes<HTMLDivElement> {
  txType: TxType;
}

export default function TypeTag({ txType, className, ...props }: TypeTagProps) {
  return (
    <TypeTagStyle className={cn("type-tag", txType, className)} {...props}>
      <p className="label">{txType === "outcome" ? "지출" : "환불"}</p>
    </TypeTagStyle>
  );
}
