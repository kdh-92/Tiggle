import cn from "classnames";

import { TypeTag } from "@/components/atoms";
import { TransactionRespDto } from "@/generated";
import { MyTransactionCellStyle } from "@/styles/components/MyTransactionCellStyle";

export default function MyTransactionCell({
  type,
  id,
  amount,
  content,
}: TransactionRespDto) {
  return (
    <MyTransactionCellStyle className={cn(type, id)}>
      <TypeTag className="tag" txType={type} size={"md"} />
      <div className={cn("amount", type)}>
        <span className="amount-unit">₩ {amount}</span>
      </div>
      <div className="transaction-cell-section">
        <p className="content">{content}</p>
      </div>
    </MyTransactionCellStyle>
  );
}
