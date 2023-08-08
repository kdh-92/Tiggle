import cn from "classnames";

import MenuButton from "@/components/atoms/MenuButton/MenuButton";
import TypeTag from "@/components/atoms/typeTag/TypeTag";
import {
  PostHeaderStyle,
  StyledPostHeaderDetail,
  StyledPostHeaderTitle,
} from "@/styles/components/PostHeaderStyle";
import { TxType } from "@/types";

interface PostHeaderProps {
  id: number;
  title: string;
  amount: number;
  txType: TxType;
  user: {
    name: string;
    profileUrl: string;
  };
  date: string;
  category: string;
  asset: string;
}

export default function PostHeader({
  id,
  title,
  amount,
  txType,
  user,
  date,
  category,
  asset,
}: PostHeaderProps) {
  return (
    <PostHeaderStyle id={id}>
      <StyledPostHeaderTitle>
        <TypeTag className="tag" txType={txType} />
        <div className={cn("amount", txType)}>
          <span className="amount-unit">₩</span>
          <span className="amount-number">{amount}</span>
        </div>
        <p className="title">{title}</p>
      </StyledPostHeaderTitle>

      <StyledPostHeaderDetail>
        <div className="user">
          <img
            className="user-profile"
            alt="user profile"
            src={user.profileUrl}
          />
          <p className="user-name">{user.name}</p>
        </div>
        <div className="item-wrapper">
          <div className="item date">
            <p className="item-title">지출일자</p>
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
