import { useState } from "react";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import ReplyToggleButton from "@/components/atoms/ReplyToggleButton/ReplyToggleButton";
import TextArea from "@/components/atoms/TextArea/TextArea";
import { CommentApiService, CommentRespDto } from "@/generated";
import {
  CommentCellStyle,
  CommentStyle,
  RepliesSectionStyle,
} from "@/styles/components/CommentCellStyle";
import { TxType } from "@/types";
import { useQuery } from "@tanstack/react-query";

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
            <p className="date">{createdAt}</p>
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
              <div
                key={`comment -${id}-reply-${reply.id}`}
                className="reply-cell"
              >
                <img
                  className="profile"
                  src={
                    reply.sender.profileUrl ?? "/assets/user-placeholder.png"
                  }
                  alt={`${reply.sender.nickname} profile`}
                />
                <div className="wrapper">
                  <div>
                    <p className="name">{reply.sender.nickname}</p>
                    <p className="date">{reply.createdAt}</p>
                  </div>
                  <p className="content">{reply.content}</p>
                </div>
              </div>
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
