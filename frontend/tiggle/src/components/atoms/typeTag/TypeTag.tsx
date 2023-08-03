import cn from "classnames";

import { StyledTypeTag } from "@/styles/components/TypeTagStyle";
import { TxType } from "@/types";

interface TypeTagProps {
  txType: TxType;
}

export default function typeTag({ txType }: TypeTagProps) {
  return (
    <StyledTypeTag className={cn("type-tag", txType)}>
      <p className="label">{txType === "outcome" ? "지출" : "환불"}</p>
    </StyledTypeTag>
  );
}
