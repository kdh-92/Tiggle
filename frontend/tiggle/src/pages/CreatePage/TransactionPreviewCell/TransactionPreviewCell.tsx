import cn from "classnames";

import { TypeTag } from "@/components/atoms";
import { TxType } from "@/types";
import { TransactionRespDto } from "@/generated";
import { convertTxTypeToWord } from "@/utils/txType";

import { TransactionPreviewCellStyle } from "./TransactionPreviewCellStyle";

interface TransactionPreviewCellProps
  extends Pick<TransactionRespDto, "content" | "reason" | "amount"> {
  type: TxType;
}

const TransactionPreviewCell = ({
  type,
  amount,
  content,
  reason,
}: TransactionPreviewCellProps) => {
  return (
    <TransactionPreviewCellStyle>
      <p className="cell-label">원본 {convertTxTypeToWord()}</p>
      <div className="cell-container">
        <div className="cell-contents-wrapper">
          <TypeTag txType={type!} size={"md"} />
          <p className={cn("amount", type)}>₩ {amount}</p>
        </div>
        <div className="cell-contents-wrapper">
          <p className="content">{content}</p>
          <p className="reason">{reason}</p>
        </div>
      </div>
    </TransactionPreviewCellStyle>
  );
};

export default TransactionPreviewCell;
