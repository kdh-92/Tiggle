import { useSelector } from "react-redux";

import cn from "classnames";
import dayjs from "dayjs";

import { Menu, MenuItem } from "@/components/atoms";
import TypeTag from "@/components/atoms/TypeTag/TypeTag";
import { MemberDto, TransactionRespDto } from "@/generated";
import {
  PostHeaderStyle,
  StyledPostHeaderDetail,
  StyledPostHeaderTitle,
} from "@/pages/DetailPage/PostHeader/PostHeaderStyle";
import { RootState } from "@/store";
import { convertTxTypeToWord } from "@/utils/txType";

export interface PostHeaderProps
  extends Pick<TransactionRespDto, "id" | "content" | "amount" | "date"> {
  // TODO: api response 변경된 후, TransactionDto 에서 Pick 하는 것으로 수정
  sender: MemberDto;
  // category: string;
  // asset: string;
}

export default function PostHeader({
  id,
  content,
  amount,
  date,
  sender,
  // category,
  // asset,
}: PostHeaderProps) {
  const txType = useSelector((state: RootState) => state.detailPage.txType);

  return (
    <PostHeaderStyle id={`post-header-${id}`}>
      <StyledPostHeaderTitle>
        <TypeTag className="tag" txType={txType} size={"md"} />
        <div className={cn("amount", txType)}>
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
            <p className="item-title">{convertTxTypeToWord(txType)}일자</p>
            <p className="item-data">{dayjs(date).format("YYYY.MM.DD")}</p>
          </div>
          {/*<div className="item">*/}
          {/*  <p className="item-title">자산</p>*/}
          {/*  <p className="item-data">{asset}</p>*/}
          {/*</div>*/}
          {/*<div className="item">*/}
          {/*  <p className="item-title">카테고리</p>*/}
          {/*  <p className="item-data">{category}</p>*/}
          {/*</div>*/}
        </div>
      </StyledPostHeaderDetail>

      <Menu className="post-header-menu">
        <MenuItem label="수정하기" />
        <MenuItem label="환불하기" />
        <MenuItem label="삭제하기" />
      </Menu>
    </PostHeaderStyle>
  );
}
