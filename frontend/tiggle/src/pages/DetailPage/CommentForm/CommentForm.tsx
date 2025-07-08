import { FormHTMLAttributes } from "react";
import { SubmitHandler, useForm, Controller } from "react-hook-form";

import { useMutation, useQueryClient } from "@tanstack/react-query";
import { Avatar } from "antd";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import { CommentApiService } from "@/generated";
import useAuth from "@/hooks/useAuth";
import useMessage from "@/hooks/useMessage";
import { CommentSenderStyle } from "@/pages/DetailPage/CommentCell/CommentCellStyle";
import { CommentFormStyle } from "@/pages/DetailPage/CommentForm/CommentFormStyle";
import { commentKeys, reactionKeys } from "@/query/queryKeys";
import { convertTxTypeToColor } from "@/utils/txType";

interface CommentFormProps extends FormHTMLAttributes<HTMLFormElement> {
  txId: number;
}

interface CommentFormInputs {
  comment: string;
}

export default function CommentForm({ txId, ...props }: CommentFormProps) {
  const queryClient = useQueryClient();
  const { isLogin, profile, checkIsLogin } = useAuth();
  const txType = "OUTCOME";
  const messageApi = useMessage();
  const {
    control,
    handleSubmit,
    reset,
    formState: { isValid },
  } = useForm<CommentFormInputs>();

  const { mutate: createComment } = useMutation(async (content: string) =>
    CommentApiService.createComment({
      txId,
      content,
    }),
  );

  const onSubmit: SubmitHandler<CommentFormInputs> = ({ comment }, event) => {
    event?.preventDefault();
    createComment(comment, {
      onSuccess: () => {
        messageApi.open({ type: "success", content: "댓글이 등록되었습니다." });
        reset({ comment: "" });
        queryClient.invalidateQueries({
          queryKey: commentKeys.list(txId),
        });
        queryClient.invalidateQueries({
          queryKey: reactionKeys.detail(txId),
        });
      },
    });
  };

  return (
    <CommentFormStyle
      {...props}
      onSubmit={handleSubmit(onSubmit)}
      className={txType}
    >
      <CommentSenderStyle>
        {isLogin && profile ? (
          <>
            <Avatar size={32} src={profile.data?.profileUrl} />
            <p className="name">{profile.data?.nickname}</p>
          </>
        ) : (
          <>
            <Avatar size={32} />
            <p className="name">사용자</p>
          </>
        )}
      </CommentSenderStyle>

      <Controller
        name="comment"
        control={control}
        rules={{ required: true }}
        render={({ field }) => (
          <textarea
            className="comment"
            placeholder="댓글 남기기"
            rows={2}
            onFocus={() => checkIsLogin()}
            {...field}
          />
        )}
      />

      <div className="button-wrapper">
        <CTAButton
          size="md"
          variant="secondary"
          color={convertTxTypeToColor()}
          type="submit"
          disabled={!isValid}
        >
          댓글 등록
        </CTAButton>
      </div>
    </CommentFormStyle>
  );
}
