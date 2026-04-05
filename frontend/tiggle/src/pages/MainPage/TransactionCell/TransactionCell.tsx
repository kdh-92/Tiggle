// import { Frown, MessageSquare, Smile } from "react-feather";
import { MessageSquare, ThumbsDown, ThumbsUp } from "react-feather";
import { useNavigate } from "react-router-dom";

import { Avatar } from "antd";
import cn from "classnames";

import TypeTag from "@/components/atoms/TypeTag/TypeTag";
import { TransactionDtoWithCount } from "@/generated/models/TransactionDtoWithCount";
import { TransactionCellStyle } from "@/pages/MainPage/TransactionCell/TransactionCellStyle";
import { getProfileImageUrl } from "@/utils/imageUrl";
import timeDiff from "@/utils/timeDIff";

export default function TransactionCell({
  dto,
  upCount,
  downCount,
  commentCount,
}: TransactionDtoWithCount) {
  const navigate = useNavigate();
  const type = "OUTCOME";

  const handleGoDetail: React.MouseEventHandler<HTMLDivElement> = () => {
    navigate(`/detail/${dto.id}`);
  };

  return (
    <div onClick={handleGoDetail}>
      <TransactionCellStyle className={cn(type, dto.id)}>
        <TypeTag className="tag" txType={type} size={"md"} />
        <div className={cn("amount", type)}>
          <span className="amount-unit">₩</span>
          <span className="amount-number">
            {dto.amount.toLocaleString("ko-KR")}
          </span>
        </div>
        <div className="transaction-cell-section">
          <p className="content">{dto.content}</p>
          <p className="reason">{dto.reason}</p>
        </div>
        <div className="transaction-cell-footer">
          <div className="user">
            {dto.member!.profileUrl ? (
              <img
                className="user-profile"
                alt="member profile"
                src={getProfileImageUrl(dto.member!.profileUrl)}
              />
            ) : (
              <Avatar />
            )}
            <div>
              <p className="user-name">{dto.member!.nickname}</p>
              <p className="user-createdAt">{timeDiff(dto.createdAt!)}</p>
            </div>
          </div>
          <div className="icon-unit">
            <div className="reaction">
              <div className="reaction-up">
                <ThumbsUp className="label-icon" />
                <span>{upCount}</span>
              </div>
              <div className="reaction-down">
                <ThumbsDown className="label-icon" />
                <span>{downCount}</span>
              </div>
            </div>
            <div className="comment">
              <MessageSquare className="label-icon" />
              <span>{commentCount}</span>
            </div>
          </div>
        </div>
      </TransactionCellStyle>
    </div>
  );
}
