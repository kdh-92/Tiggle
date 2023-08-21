import { useState } from "react";

import { useQuery } from "@tanstack/react-query";
import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import ReplyToggleButton from "@/components/atoms/ReplyToggleButton/ReplyToggleButton";
import TextArea from "@/components/atoms/TextArea/TextArea";
import { CommentApiService, CommentRespDto } from "@/generated";
import {
  CommentCellStyle,
  CommentStyle,
  RepliesSectionStyle,
  ReplyCellStyle,
} from "@/styles/components/CommentCellStyle";
import { TxType } from "@/types";
import { calculateDateTimeDiff } from "@/utils/date";

interface CommentCellProps
  extends Pick<
    CommentRespDto,
    "id" | "txId" | "content" | "createdAt" | "childCount" | "sender"
  > {
  type: TxType;
}

export default function CommentCell({
  id,
  type,
  txId,
  content,
  createdAt,
  childCount,
  sender,
}: CommentCellProps) {
  const [replyOpen, setReplyOpen] = useState(false);

  const toggleReplySection = () => {
    setReplyOpen(!replyOpen);
  };

  console.log(id, txId);

  const { data: repliesData } = useQuery({
    queryKey: ["comment", "replies", id],
    queryFn: async () => CommentApiService.getAllCommentsByCommentId(id),
    enabled: replyOpen,
    staleTime: 1000 * 60 * 10,
  });

  return (
    <CommentCellStyle>
      <img
        className="comment-cell-profile"
        src={sender.profileUrl ?? "/assets/user-placeholder.png"}
        alt={`${sender.nickname} profile`}
      />

      <div>
        <CommentStyle className={type}>
          <div>
            <p className="name">{sender.nickname}</p>
            <p className="date">{calculateDateTimeDiff(createdAt)}</p>
          </div>
          <p className="content">{content}</p>
          <ReplyToggleButton
            txType={type}
            open={replyOpen}
            repliesCount={childCount}
            onClick={toggleReplySection}
          />
        </CommentStyle>

        {replyOpen && (
          <RepliesSectionStyle>
            {childCount > 0 && <div className="reply-cell-divider" />}

            {repliesData?.content?.map(reply => (
              <ReplyCell key={`comment-reply-${reply.id}`} {...reply} />
            ))}

            <div className="input">
              <TextArea variant="filled" placeholder="답글 남기기" />
              <CTAButton size="md">답글 등록</CTAButton>
            </div>
          </RepliesSectionStyle>
        )}
      </div>
    </CommentCellStyle>
  );
}

interface ReplyCellProps
  extends Pick<CommentRespDto, "id" | "content" | "createdAt" | "sender"> {}

function ReplyCell({ id, content, createdAt, sender }: ReplyCellProps) {
  return (
    <ReplyCellStyle id={`comment-reply-${id}`}>
      <img
        className="reply-profile"
        src={sender.profileUrl ?? "/assets/user-placeholder.png"}
        alt={`${sender.nickname} profile`}
      />
      <div className="reply-info">
        <div>
          <p className="name">{sender.nickname}</p>
          <p className="date">{calculateDateTimeDiff(createdAt)}</p>
        </div>
        <p className="reply-content">{content}</p>
      </div>
    </ReplyCellStyle>
  );
}
