import { Frown, MessageSquare, Smile } from "react-feather";

import { Avatar } from "antd";
import cn from "classnames";

import TypeTag from "@/components/atoms/typeTag/TypeTag";
import { TransactionCellStyle } from "@/styles/components/TransactionCellStyle";
import { TxType } from "@/types";

export type TransactionCellProps = {
  id: number;
  content: string;
  reason: string;
  amount: number;
  type: TxType;
  user: {
    name: string;
    imageUrl: string;
  };
  createdAt: string;
  number: number;
};

export default function TransactionCell({
  id,
  type,
  amount,
  content,
  reason,
  user,
  createdAt,
  number,
}: TransactionCellProps) {
  return (
    <TransactionCellStyle className={cn(type, id)}>
      <TypeTag className="tag" txType={type} />
      <div className={cn("amount", type)}>
        <span className="amount-unit">₩ {amount}</span>
      </div>
      <div className="feed-section">
        <p className="content">{content}</p>
        <p className="reason">{reason}</p>
      </div>
      <div className="feed-footer">
        <div className="user">
          {user.imageUrl ? (
            <img
              className="user-profile"
              alt="user profile"
              src={user.imageUrl}
            />
          ) : (
            <Avatar />
          )}
          <div>
            <p className="user-name">{user.name}</p>
            <p className="user-createdAt">{createdAt}</p>
          </div>
        </div>
        <div className="icon-unit">
          <div className="reaction">
            <div className="reaction-smile">
              <Smile className="label-icon" />
              <span>{number}</span>
            </div>
            <div className="reaction-frown">
              <Frown className="label-icon" />
              <span>{number}</span>
            </div>
          </div>
          <div className="comment">
            <MessageSquare className="label-icon" />
            <span>{number}</span>
          </div>
        </div>
      </div>
    </TransactionCellStyle>
  );
}
