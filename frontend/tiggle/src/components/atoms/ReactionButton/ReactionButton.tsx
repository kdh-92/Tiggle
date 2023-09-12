import { ButtonHTMLAttributes } from "react";
import { Frown, Smile } from "react-feather";

import cn from "classnames";

import { ReactionButtonStyle } from "@/styles/components/ReactionButtonStyle";
import { Reaction, ReactionType, TxType } from "@/types";

interface ReactionButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  tx: TxType;
  reaction: ReactionType;
  number: number;
  checked?: boolean;
}

export default function ReactionButton({
  tx,
  reaction,
  number,
  checked,
  ...props
}: ReactionButtonProps) {
  return (
    <ReactionButtonStyle className={cn({ checked }, tx)} {...props}>
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
