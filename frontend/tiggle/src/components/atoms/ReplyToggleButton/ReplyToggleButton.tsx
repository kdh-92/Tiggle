import { ButtonHTMLAttributes } from "react";
import { MinusSquare, PlusSquare } from "react-feather";

import cn from "classnames";

import { ReplyToggleButtonStyle } from "@/styles/components/ReplyToggleButtonStyle";
import { TxType } from "@/types";

interface ReplyToggleButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement> {
  txType: TxType;
  open: boolean;
  repliesCount: number;
}

export default function ReplyToggleButton({
  txType,
  open,
  repliesCount,
  className,
  ...props
}: ReplyToggleButtonProps) {
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
