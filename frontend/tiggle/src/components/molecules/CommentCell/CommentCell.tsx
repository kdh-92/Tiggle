import { useState } from "react";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import ReplyToggleButton from "@/components/atoms/ReplyToggleButton/ReplyToggleButton";
import TextArea from "@/components/atoms/TextArea/TextArea";
import {
  CommentCellStyle,
  CommentStyle,
  RepliesSectionStyle,
} from "@/styles/components/CommentCellStyle";
import { TxType } from "@/types";

export interface Comment {
  id: number;
  user: {
    name: string;
    profileUrl: string;
  };
  createdAt: string;
  content: string;
  replies: Array<Omit<Comment, "replies">>;
}

interface CommentCellProps {
  txType: TxType;
  comment: Comment;
}

export default function CommentCell({ txType, comment }: CommentCellProps) {
  const [replyOpen, setReplyOpen] = useState(false);

  const toggleReplySection = () => {
    setReplyOpen(!replyOpen);
  };

  return (
    <CommentCellStyle>
      <img
        className="comment-cell-profile"
        src={comment.user.profileUrl}
        alt={`${comment.user.name} profile`}
      />

      <div>
        <CommentStyle className={txType}>
          <div>
            <p className="name">{comment.user.name}</p>
            <p className="date">{comment.createdAt}</p>
          </div>
          <p className="content">{comment.content}</p>
          <ReplyToggleButton
            txType={txType}
            open={replyOpen}
            repliesCount={comment.replies.length}
            onClick={toggleReplySection}
          />
        </CommentStyle>

        {replyOpen && (
          <RepliesSectionStyle>
            {comment.replies.length > 0 && (
              <div className="reply-cell-divider" />
            )}

            {comment.replies.map(reply => (
              <div
                key={`comment -${comment.id}-reply-${reply.id}`}
                className="reply-cell"
              >
                <img
                  className="profile"
                  src={reply.user.profileUrl}
                  alt={`${reply.user.name} profile`}
                />
                <div className="wrapper">
                  <div>
                    <p className="name">{reply.user.name}</p>
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
