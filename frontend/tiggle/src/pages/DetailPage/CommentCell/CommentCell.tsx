import { useState } from "react";
import { SubmitHandler, useForm, Controller } from "react-hook-form";
import { useSelector } from "react-redux";

import { useMutation, useQuery } from "@tanstack/react-query";
import { Avatar } from "antd";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import TextArea from "@/components/atoms/TextArea/TextArea";
import { CommentApiService, CommentRespDto } from "@/generated";
import useMessage from "@/hooks/useMessage";
import {
  CommentCellStyle,
  CommentRepliesStyle,
  ReplyCellStyle,
  CommentSenderStyle,
  ReplyFormStyle,
} from "@/pages/DetailPage/CommentCell/CommentCellStyle";
import queryClient from "@/query/queryClient";
import { commentKeys } from "@/query/queryKeys";
import { RootState } from "@/store";
import { calculateDateTimeDiff } from "@/utils/date";
import { convertTxTypeToColor } from "@/utils/txType";

import ReplyToggleButton from "../ReplyToggleButton/ReplyToggleButton";

const TEMP_USER_ID = 1;

export interface CommentCellProps
  extends Pick<
    CommentRespDto,
    "id" | "txId" | "content" | "createdAt" | "childCount" | "sender"
  > {
  receiverId: number;
}

export default function CommentCell({
  id,
  txId,
  content,
  createdAt,
  childCount,
  sender,
  receiverId,
}: CommentCellProps) {
  const messageApi = useMessage();
  const [replyOpen, setReplyOpen] = useState(false);

  const toggleReplySection = () => {
    setReplyOpen(!replyOpen);
  };

  const { data: repliesData } = useQuery(
    commentKeys.reply(id!),
    async () => CommentApiService.getAllCommentsByCommentId(id!),
    {
      staleTime: 1000 * 60 * 10,
      enabled: replyOpen,
    },
  );

  const { mutate: createReply } = useMutation(async (comment: string) =>
    CommentApiService.createComment({
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
    <CommentCellStyle>
      <CommentSenderStyle>
        <Avatar
          size={32}
          src={sender!.profileUrl}
          alt={`${sender!.nickname} profile`}
        />
        <div>
          <p className="name">{sender!.nickname}</p>
          <p className="date">{calculateDateTimeDiff(createdAt!)}</p>
        </div>
      </CommentSenderStyle>

      <p className="content">{content}</p>

      <ReplyToggleButton
        open={replyOpen}
        repliesCount={childCount!}
        onClick={toggleReplySection}
      />

      {replyOpen && (
        <CommentRepliesStyle>
          {childCount! > 0 && <div className="divider" />}

          {repliesData?.content?.map(reply => (
            <ReplyCell key={`comment-reply-${reply.id}`} {...reply} />
          ))}

          <ReplyForm onSubmit={onSubmitReply} />
        </CommentRepliesStyle>
      )}
    </CommentCellStyle>
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
          src={sender!.profileUrl ?? "/assets/user-placeholder.png"}
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

interface ReplyInputs {
  reply: string;
}

interface ReplyFormProps {
  onSubmit: (reply: string) => void;
}

function ReplyForm({ onSubmit }: ReplyFormProps) {
  const txType = useSelector((state: RootState) => state.detailPage.txType);
  const { control, handleSubmit, reset } = useForm<ReplyInputs>();

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
          <TextArea
            variant="compact"
            color="toned"
            placeholder="답글 남기기"
            {...field}
          />
        )}
      />
      <CTAButton
        size="md"
        color={convertTxTypeToColor(txType)}
        variant="secondary"
      >
        답글 등록
      </CTAButton>
    </ReplyFormStyle>
  );
}
