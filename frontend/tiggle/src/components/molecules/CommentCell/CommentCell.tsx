import { useMemo, useState } from "react";
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
  CommentRepliesStyle,
  ReplyCellStyle,
  CommentSenderStyle,
  ReplyFormStyle,
} from "@/styles/components/CommentCellStyle";
import { TxType } from "@/types";
import { calculateDateTimeDiff } from "@/utils/date";

const TEMP_USER_ID = 1;

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

  const onSubmitReply = (reply: string) => {
    createReply(reply, {
      onSuccess: () => {
        messageApi.open({
          type: "success",
          content: "답댓글이 등록되었습니다.",
        });
        queryClient.invalidateQueries(["comment", "replies", id]);
      },
    });
  };

  return (
    <>
      {contextHolder}
      <CommentCellStyle>
        <CommentSenderStyle>
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

        <ReplyToggleButton
          txType={type}
          open={replyOpen}
          repliesCount={childCount}
          onClick={toggleReplySection}
        />

        {replyOpen && (
          <CommentRepliesStyle>
            {childCount > 0 && <div className="divider" />}

            {repliesData?.content?.map(reply => (
              <ReplyCell key={`comment-reply-${reply.id}`} {...reply} />
            ))}

            <ReplyForm type={type} onSubmit={onSubmitReply} />
          </CommentRepliesStyle>
        )}
      </CommentCellStyle>
    </>
  );
}

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

interface ReplyFormProps {
  type: TxType;
  onSubmit: (reply: string) => void;
}

interface ReplyInputs {
  reply: string;
}

function ReplyForm({ type, onSubmit }: ReplyFormProps) {
  const { control, handleSubmit, reset } = useForm<ReplyInputs>();

  const btnColor = useMemo(() => {
    switch (type) {
      case "OUTCOME":
        return "peach";
      case "REFUND":
        return "blue";
      case "INCOME":
        return "green";
    }
  }, [type]);

  const handleOnSubmit: SubmitHandler<ReplyInputs> = ({ reply }) => {
    if (reply === "") return;
    onSubmit(reply);
    reset({ reply: "" });
  };

  return (
    <ReplyFormStyle onSubmit={handleSubmit(handleOnSubmit)}>
      <Controller
        name="reply"
        control={control}
        render={({ field }) => (
          <TextArea variant="filled" placeholder="답글 남기기" {...field} />
        )}
      />
      <CTAButton size="md" color={btnColor} variant="secondary">
        답글 등록
      </CTAButton>
    </ReplyFormStyle>
  );
}
