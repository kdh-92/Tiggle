import { FormHTMLAttributes } from "react";
import { SubmitHandler, useForm, Controller } from "react-hook-form";

import { useMutation } from "@tanstack/react-query";
import { message } from "antd";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import TextArea from "@/components/atoms/TextArea/TextArea";
import { CommentApiService } from "@/generated";
import queryClient from "@/query/queryClient";
import { CommentFormStyle } from "@/styles/components/CommentFormStyle";

interface CommentFormProps extends FormHTMLAttributes<HTMLFormElement> {
  txId: number;
  receiverId: number;
}

interface CommentFormInputs {
  comment: string;
}

const TEMP_USER_ID = 1;

export default function CommentForm({
  txId,
  receiverId,
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

  const onSubmit: SubmitHandler<CommentFormInputs> = ({ comment }) => {
    if (comment === "") {
      return;
    }

    createComment(comment, {
      onSuccess: () => {
        messageApi.open({ type: "success", content: "댓글이 등록되었습니다." });
        reset();
        queryClient.invalidateQueries(["transaction", "comments", txId]);
      },
    });
  };

  return (
    <>
      {contextHolder}
      <CommentFormStyle {...props} onSubmit={handleSubmit(onSubmit)}>
        <Controller
          name="comment"
          control={control}
          render={({ field }) => (
            <TextArea placeholder="댓글 남기기" {...field} />
          )}
        />
        <CTAButton size="md" type="submit">
          댓글 등록
        </CTAButton>
      </CommentFormStyle>
    </>
  );
}
