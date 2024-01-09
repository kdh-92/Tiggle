import { CommentRespDto } from "@/generated";
import { calculateDateTimeDiff } from "@/utils/date";

import { ReplyCellStyle } from "./ReplyCellStyle";
import { CommentSenderStyle } from "../CommentCell/CommentCellStyle";

interface ReplyCellProps
  extends Pick<CommentRespDto, "id" | "content" | "createdAt" | "sender"> {}

function ReplyCell({ id, content, createdAt, sender }: ReplyCellProps) {
  return (
    <ReplyCellStyle id={`comment-reply-${id}`}>
      <CommentSenderStyle className="user">
        <img
          className="profile"
          src={sender.profileUrl ?? "/assets/user-placeholder.png"}
          alt={`${sender.nickname} profile`}
        />
        <div>
          <p className="name">{sender.nickname}</p>
          <p className="date">{calculateDateTimeDiff(createdAt)}</p>
        </div>
      </CommentSenderStyle>

      <p className="content">{content}</p>
    </ReplyCellStyle>
  );
}

export default ReplyCell;
