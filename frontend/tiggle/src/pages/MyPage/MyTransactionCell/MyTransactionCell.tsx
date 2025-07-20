import cn from "classnames";

import { TypeTag } from "@/components/atoms";
import { TransactionRespDto } from "@/generated";
import { MyTransactionCellStyle } from "@/pages/MyPage/MyTransactionCell/MyTransactionCellStyle";

export default function MyTransactionCell({
  id,
  amount,
  content,
}: TransactionRespDto) {
  return (
    <MyTransactionCellStyle className={cn("OUTCOME", id)}>
      <TypeTag className="tag" txType={"OUTCOME"} size={"md"} />
      <div className={cn("amount", "OUTCOME")}>
        <span className="amount-unit">₩ {amount}</span>
      </div>
      <div className="transaction-cell-section">
        <p className="content">{content}</p>
      </div>
    </MyTransactionCellStyle>
  );
}
