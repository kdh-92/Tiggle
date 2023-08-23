import { useState } from "react";
import { SubmitHandler, useForm, Controller } from "react-hook-form";

import { useMutation, useQuery } from "@tanstack/react-query";
import { message } from "antd";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import ReplyToggleButton from "@/components/atoms/ReplyToggleButton/ReplyToggleButton";
import TextArea from "@/components/atoms/TextArea/TextArea";
import { CommentApiService, CommentRespDto } from "@/generated";
import queryClient from "@/query/queryClient";
import {
  CommentCellStyle,
  CommentStyle,
  RepliesSectionStyle,
  ReplyCellStyle,
} from "@/styles/components/CommentCellStyle";
import { TxType } from "@/types";
import { calculateDateTimeDiff } from "@/utils/date";

const TEMP_USER_ID = 1;

interface ReplyInputs {
  reply: string;
}

interface CommentCellProps
  extends Pick<
    CommentRespDto,
    "id" | "txId" | "content" | "createdAt" | "childCount" | "sender"
  > {
  type: TxType;
  receiverId: number;
}

export default function CommentCell({
  id,
  type,
  txId,
  content,
  createdAt,
  childCount,
  sender,
  receiverId,
}: CommentCellProps) {
  const [messageApi, contextHolder] = message.useMessage();
  const { control, handleSubmit, reset } = useForm<ReplyInputs>();
  const [replyOpen, setReplyOpen] = useState(false);

  const toggleReplySection = () => {
    setReplyOpen(!replyOpen);
  };

  const { data: repliesData } = useQuery({
    queryKey: ["comment", "replies", id],
    queryFn: async () => CommentApiService.getAllCommentsByCommentId(id),
    enabled: replyOpen,
    staleTime: 1000 * 60 * 10,
  });

  const { mutate: createReply } = useMutation(async (comment: string) =>
    CommentApiService.createComment(TEMP_USER_ID, {
      txId,
      senderId: TEMP_USER_ID,
      parentId: id,
      receiverId,
      content: comment,
    }),
  );

  const onSubmitReply: SubmitHandler<ReplyInputs> = ({ reply }) => {
    if (reply === "") {
      return;
    }
    createReply(reply, {
      onSuccess: () => {
        messageApi.open({
          type: "success",
          content: "답댓글이 등록되었습니다.",
        });
        reset();
        queryClient.invalidateQueries(["comment", "replies", id]);
      },
    });
  };

  return (
    <>
      {contextHolder}
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

              <form
                className="reply-cell-input"
                onSubmit={handleSubmit(onSubmitReply)}
              >
                <Controller
                  name="reply"
                  control={control}
                  render={({ field }) => (
                    <TextArea
                      variant="filled"
                      placeholder="답글 남기기"
                      {...field}
                    />
                  )}
                />
                <CTAButton size="md">답글 등록</CTAButton>
              </form>
            </RepliesSectionStyle>
          )}
        </div>
      </CommentCellStyle>
    </>
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
