// import { Frown, MessageSquare, Smile } from "react-feather";
import { MessageSquare, ThumbsDown, ThumbsUp } from "react-feather";
import { useNavigate } from "react-router-dom";

import { Avatar } from "antd";
import cn from "classnames";

import TypeTag from "@/components/atoms/typeTag/TypeTag";
import { TransactionRespDto } from "@/generated/models/TransactionRespDto";
import { TransactionCellStyle } from "@/styles/components/TransactionCellStyle";
import timeDiff from "@/utils/timeDIff";

export default function TransactionCell({
  id,
  type,
  amount,
  content,
  reason,
  member,
  createdAt,
  txUpCount,
  txDownCount,
  txCommentCount,
}: TransactionRespDto) {
  const navigate = useNavigate();

  const handleGoDetail: React.MouseEventHandler<HTMLDivElement> = () => {
    navigate(`/detail/${id}`);
  };

  return (
    <div onClick={handleGoDetail}>
      <TransactionCellStyle className={cn(type, id)}>
        <TypeTag className="tag" txType={type} size={"md"} />
        <div className={cn("amount", type)}>
          <span className="amount-unit">₩ {amount}</span>
        </div>
        <div className="transaction-cell-section">
          <p className="content">{content}</p>
          <p className="reason">{reason}</p>
        </div>
        <div className="transaction-cell-footer">
          <div className="user">
            {member.profileUrl ? (
              <img
                className="user-profile"
                alt="member profile"
                src={member.profileUrl}
              />
            ) : (
              <Avatar />
            )}
            <div>
              <p className="user-name">{member.nickname}</p>
              <p className="user-createdAt">{timeDiff(createdAt)}</p>
            </div>
          </div>
          <div className="icon-unit">
            <div className="reaction">
              <div className="reaction-up">
                <ThumbsUp className="label-icon" />
                <span>{txUpCount}</span>
              </div>
              <div className="reaction-down">
                <ThumbsDown className="label-icon" />
                <span>{txDownCount}</span>
              </div>
            </div>
            <div className="comment">
              <MessageSquare className="label-icon" />
              <span>{txCommentCount}</span>
            </div>
          </div>
        </div>
      </TransactionCellStyle>
    </div>
  );
}
