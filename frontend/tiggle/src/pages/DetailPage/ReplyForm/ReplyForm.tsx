import { Controller, SubmitHandler, useForm } from "react-hook-form";

import { CTAButton, TextArea } from "@/components/atoms";
import { convertTxTypeToColor } from "@/utils/txType";

import { ReplyFormStyle } from "./ReplyFormStyle";

interface ReplyInputs {
  reply: string;
}

interface ReplyFormProps {
  onSubmit: (reply: string) => void;
}

function ReplyForm({ onSubmit }: ReplyFormProps) {
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
      <CTAButton size="md" color={convertTxTypeToColor()} variant="secondary">
        답글 등록
      </CTAButton>
    </ReplyFormStyle>
  );
}

export default ReplyForm;
