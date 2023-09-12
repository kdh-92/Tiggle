import cn from "classnames";
import dayjs from "dayjs";

import MenuButton from "@/components/atoms/MenuButton/MenuButton";
import TypeTag from "@/components/atoms/typeTag/TypeTag";
import { MemberDto, TransactionRespDto } from "@/generated";
import {
  PostHeaderStyle,
  StyledPostHeaderDetail,
  StyledPostHeaderTitle,
} from "@/styles/components/PostHeaderStyle";

interface PostHeaderProps
  extends Pick<
    TransactionRespDto,
    "id" | "type" | "content" | "amount" | "date"
  > {
  // TODO: api response 변경된 후, TransactionDto 에서 Pick 하는 것으로 수정
  sender: MemberDto;
  category: string;
  asset: string;
}

export default function PostHeader({
  id,
  type,
  content,
  amount,
  date,
  sender,
  category,
  asset,
}: PostHeaderProps) {
  return (
    <PostHeaderStyle id={`post-header-${id}`}>
      <StyledPostHeaderTitle>
        <TypeTag className="tag" txType={type} />
        <div className={cn("amount", type)}>
          <span className="amount-unit">₩</span>
          <span className="amount-number">{amount}</span>
        </div>
        <p className="title">{content}</p>
      </StyledPostHeaderTitle>

      <StyledPostHeaderDetail>
        <div className="user">
          <img
            className="user-profile"
            alt="user profile"
            src={sender.profileUrl ?? "/assets/user-placeholder.png"}
          />
          <p className="user-name">{sender.nickname}</p>
        </div>
        <div className="item-wrapper">
          <div className="item date">
            <p className="item-title">
              {txType === "OUTCOME" ? "지출일자" : "수익일자"}
            </p>
            <p className="item-data">{date}</p>
          </div>
          <div className="item">
            <p className="item-title">자산</p>
            <p className="item-data">{asset}</p>
          </div>
          <div className="item">
            <p className="item-title">카테고리</p>
            <p className="item-data">{category}</p>
          </div>
        </div>
      </StyledPostHeaderDetail>

      <MenuButton className="post-header-menu" />
    </PostHeaderStyle>
  );
}
