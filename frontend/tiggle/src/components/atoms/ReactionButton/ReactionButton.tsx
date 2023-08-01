import { Frown, Smile } from "react-feather";

import cn from "classnames";

import { StyledReactionButton } from "@/styles/components/ReactionButtonStyle";
import { TxType } from "@/types";

interface ReactionButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  tx: TxType;
  reaction: "good" | "bad";
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
    <StyledReactionButton className={cn({ checked }, tx)} {...props}>
      <div className="label">
        {reaction === "good" && (
          <Smile className="label-icon" strokeWidth={1.5} />
        )}
        {reaction === "bad" && (
          <Frown className="label-icon" strokeWidth={1.5} />
        )}
        <span className="label-text">
          {reaction === "good" ? "칭찬해요" : "아까워요"}
        </span>
      </div>
      <span className="number">{number}</span>
    </StyledReactionButton>
  );
}
