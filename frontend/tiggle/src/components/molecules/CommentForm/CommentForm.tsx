import { FormHTMLAttributes } from "react";
import { SubmitHandler, useForm, Controller } from "react-hook-form";
import { useSelector } from "react-redux";

import { useMutation } from "@tanstack/react-query";
import { Avatar, message } from "antd";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import { CommentApiService } from "@/generated";
import useLogin from "@/hooks/useAuth";
import queryClient from "@/query/queryClient";
import { RootState } from "@/store";
import { CommentSenderStyle } from "@/styles/components/CommentCellStyle";
import { CommentFormStyle } from "@/styles/components/CommentFormStyle";
import { convertTxTypeToColor } from "@/utils/txType";

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
  const { isLogin, profile, checkIsLogin } = useLogin();
  const txType = useSelector((state: RootState) => state.detailPage.txType);
  const [messageApi, contextHolder] = message.useMessage();
  const {
    control,
    handleSubmit,
    reset,
    formState: { isValid },
  } = useForm<CommentFormInputs>();

  const { mutate: createComment } = useMutation(async (content: string) =>
    CommentApiService.createComment(TEMP_USER_ID, {
      txId,
      senderId: profile.id,
      receiverId: receiverId,
      content,
    }),
  );

  const onSubmit: SubmitHandler<CommentFormInputs> = ({ comment }, event) => {
    event.preventDefault();
    createComment(comment, {
      onSuccess: () => {
        messageApi.open({ type: "success", content: "댓글이 등록되었습니다." });
        reset({ comment: "" });
        queryClient.invalidateQueries(["transaction", "comments", txId]);
      },
    });
  };

  return (
    <>
      {contextHolder}
      <CommentFormStyle
        {...props}
        onSubmit={handleSubmit(onSubmit)}
        className={txType}
      >
        <CommentSenderStyle>
          {isLogin && profile ? (
            <>
              <Avatar size={32} src={profile.profileUrl} />
              <p className="name">{profile.nickname}</p>
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
            color={convertTxTypeToColor(txType)}
            type="submit"
            disabled={!isValid}
          >
            댓글 등록
          </CTAButton>
        </div>
      </CommentFormStyle>
    </>
  );
}
