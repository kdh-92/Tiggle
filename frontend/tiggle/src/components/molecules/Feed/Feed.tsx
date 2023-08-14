import { Frown, MessageSquare, Smile } from "react-feather";

import { Avatar } from "antd";
import cn from "classnames";

import TypeTag from "@/components/atoms/typeTag/TypeTag";
import { FeedStyle } from "@/styles/components/FeedStyle";
import { TxType } from "@/types";

export type FeedProps = {
  id: number;
  title: string;
  content: string;
  amount: number;
  txType: TxType;
  user: {
    name: string;
    profileUrl: string;
  };
  createdAt: string;
  number: number;
};

export default function Feed({
  id,
  txType,
  amount,
  title,
  content,
  user,
  createdAt,
  number,
}: FeedProps) {
  return (
    <FeedStyle className={cn(txType, id)}>
      <TypeTag className="tag" txType={txType} />
      <div className={cn("amount", txType)}>
        <span className="amount-unit">₩ {amount}</span>
      </div>
      <div className="feed-section">
        <p className="title">{title}</p>
        <p className="content">{content}</p>
      </div>
      <div className="feed-footer">
        <div className="user">
          {user.profileUrl ? (
            <img
              className="user-profile"
              alt="user profile"
              src={user.profileUrl}
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
    </FeedStyle>
  );
}
