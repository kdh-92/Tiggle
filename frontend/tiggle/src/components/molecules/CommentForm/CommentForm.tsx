import { FormHTMLAttributes, useMemo } from "react";
import { SubmitHandler, useForm, Controller } from "react-hook-form";

import { useMutation } from "@tanstack/react-query";
import { message } from "antd";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import { CommentApiService } from "@/generated";
import queryClient from "@/query/queryClient";
import { CommentSenderStyle } from "@/styles/components/CommentCellStyle";
import { CommentFormStyle } from "@/styles/components/CommentFormStyle";
import { TxType } from "@/types";

interface CommentFormProps extends FormHTMLAttributes<HTMLFormElement> {
  txId: number;
  type: TxType;
  receiverId: number;
}

interface CommentFormInputs {
  comment: string;
}

const TEMP_USER_ID = 1;

export default function CommentForm({
  txId,
  receiverId,
  type,
  ...props
}: CommentFormProps) {
  const [messageApi, contextHolder] = message.useMessage();
  const { control, handleSubmit, reset } = useForm<CommentFormInputs>();

  const { mutate: createComment } = useMutation(async (content: string) =>
    CommentApiService.createComment(TEMP_USER_ID, {
      txId,
      senderId: TEMP_USER_ID,
      receiverId: receiverId,
      content,
    }),
  );

  const onSubmit: SubmitHandler<CommentFormInputs> = ({ comment }, event) => {
    event.preventDefault();
    if (comment === "") return;

    createComment(comment, {
      onSuccess: () => {
        messageApi.open({ type: "success", content: "댓글이 등록되었습니다." });
        reset({ comment: "" });
        queryClient.invalidateQueries(["transaction", "comments", txId]);
      },
    });
  };

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

  return (
    <>
      {contextHolder}
      <CommentFormStyle {...props} onSubmit={handleSubmit(onSubmit)}>
        <CommentSenderStyle>
          <img className="profile" />
          <p className="name">내 이름</p>
        </CommentSenderStyle>

        <Controller
          name="comment"
          control={control}
          render={({ field }) => (
            <textarea
              className="comment"
              placeholder="댓글 남기기"
              rows={2}
              {...field}
            />
          )}
        />

        <div className="button-wrapper">
          <CTAButton
            size="md"
            variant="secondary"
            color={btnColor}
            type="submit"
          >
            댓글 등록
          </CTAButton>
        </div>
      </CommentFormStyle>
    </>
  );
}
