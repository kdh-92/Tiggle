import { ButtonHTMLAttributes } from "react";
import { MinusSquare, PlusSquare } from "react-feather";

import cn from "classnames";

import { ReplyToggleButtonStyle } from "@/pages/DetailPage/ReplyToggleButton/ReplyToggleButtonStyle";

export interface ReplyToggleButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement> {
  open: boolean;
  repliesCount: number;
}

export default function ReplyToggleButton({
  open,
  repliesCount,
  className,
  ...props
}: ReplyToggleButtonProps) {
  const txType = "OUTCOME";

  return (
    <ReplyToggleButtonStyle className={cn(txType, className)} {...props}>
      {open ? (
        <MinusSquare className="icon" />
      ) : (
        <PlusSquare className="icon" />
      )}
      {repliesCount > 0 && <p className="count">답글 {repliesCount}개</p>}
      <p className="text">{open ? "답글 숨기기" : "답글 달기"}</p>
    </ReplyToggleButtonStyle>
  );
}
