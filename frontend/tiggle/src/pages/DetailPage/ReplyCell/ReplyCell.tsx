import { CommentRespDto } from "@/generated";
import { calculateDateTimeDiff } from "@/utils/date";

import { ReplyCellStyle } from "./ReplyCellStyle";
import { CommentSenderStyle } from "../CommentCell/CommentCellStyle";
import { getProfileImageUrl } from "@/utils/imageUrl";

interface ReplyCellProps
  extends Pick<CommentRespDto, "id" | "content" | "createdAt" | "sender"> {}

function ReplyCell({ id, content, createdAt, sender }: ReplyCellProps) {
  return (
    <ReplyCellStyle id={`comment-reply-${id}`}>
      <CommentSenderStyle className="user">
        <img
          className="profile"
          src={
            getProfileImageUrl(sender!.profileUrl) ??
            "/assets/user-placeholder.png"
          }
          alt={`${sender!.nickname} profile`}
        />
        <div>
          <p className="name">{sender!.nickname}</p>
          <p className="date">{calculateDateTimeDiff(createdAt!)}</p>
        </div>
      </CommentSenderStyle>

      <p className="content">{content}</p>
    </ReplyCellStyle>
  );
}

export default ReplyCell;
