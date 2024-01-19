import { ButtonHTMLAttributes } from "react";
import { Frown, Smile } from "react-feather";
import { useSelector } from "react-redux";

import cn from "classnames";

import { ReactionButtonStyle } from "@/pages/DetailPage/ReactionButton/ReactionButtonStyle";
import { RootState } from "@/store";
import { Reaction, ReactionType } from "@/types";

export interface ReactionButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement> {
  reaction: ReactionType;
  number: number;
  checked?: boolean;
}

export default function ReactionButton({
  reaction,
  number,
  checked,
  ...props
}: ReactionButtonProps) {
  const txType = useSelector((state: RootState) => state.detailPage.txType);

  return (
    <ReactionButtonStyle className={cn({ checked }, txType)} {...props}>
      <div className="label">
        {reaction === Reaction.Up && (
          <Smile className="label-icon" strokeWidth={1.5} />
        )}
        {reaction === Reaction.Down && (
          <Frown className="label-icon" strokeWidth={1.5} />
        )}
        <span className="label-text">
          {reaction === Reaction.Up ? "칭찬해요" : "아까워요"}
        </span>
      </div>
      <span className="number">{number}</span>
    </ReactionButtonStyle>
  );
}
