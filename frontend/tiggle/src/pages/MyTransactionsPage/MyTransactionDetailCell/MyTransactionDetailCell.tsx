import cn from "classnames";

import { TypeTag } from "@/components/atoms";
import { formatNumber } from "@/utils/format";

import { MyTransactionDetailCellStyle } from "./MyTransactionDetailCellStyle";

export interface MyTransactionDetailCellProps {
  id: number;
  amount: number;
  content: string;
  reason: string;
}

const MyTransactionDetailCell = ({
  id,
  amount,
  content,
  reason,
}: MyTransactionDetailCellProps) => {
  const type = "OUTCOME";

  return (
    <a href={`/detail/${id}`}>
      <MyTransactionDetailCellStyle>
        <div className="header">
          <TypeTag txType={type} size="md" />
          <div className={cn("amount", type)}>
            <span>₩</span>
            <span>{formatNumber(amount)}</span>
          </div>
        </div>

        <div className="body">
          <p className="content">{content}</p>
          <p className="reason">{reason}</p>
        </div>
      </MyTransactionDetailCellStyle>
    </a>
  );
};

export default MyTransactionDetailCell;
