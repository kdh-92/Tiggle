import { SubmitHandler } from "react-hook-form";
import { useNavigate } from "react-router-dom";

import { useMutation } from "@tanstack/react-query";
import dayjs from "dayjs";

import CreateForm, { FormInputs } from "@/components/templates/CreateForm";
import { CreatePageStyle } from "@/styles/pages/CreatePageStyle";
import { useMessage } from "@/templates/GeneralTemplate";
import { TxType } from "@/types";

import { createTransaction, TransactionFormData } from "./request";

interface CreatePageProps {
  type: Exclude<TxType, "REFUND">;
}
const CreatePage = ({ type }: CreatePageProps) => {
  const navigate = useNavigate();
  const { messageApi } = useMessage();

  const { mutate } = useMutation(createTransaction);

  const handleOnSubmit: SubmitHandler<FormInputs> = data => {
    const { tags, date, imageUrl, ...rest } = data;
    const formData: TransactionFormData = {
      dto: {
        type,
        memberId: 1,
        tagNames: tags?.join(", "),
        date: dayjs(date).toISOString(),
        ...rest,
      },
      multipartFile: imageUrl.item(0),
    };

    mutate(formData, {
      onSuccess: ({ id }) => {
        messageApi.open({ type: "success", content: "거래가 등록되었습니다." });
        navigate(`/detail/${id}`);
      },
      onError: error => {
        messageApi.open({
          type: "error",
          content: "거래가 등록에 실패했습니다.",
        });
        console.log(error);
      },
    });
  };

  const handleOnCancel = () => {
    navigate(-1);
  };

  return (
    <CreatePageStyle>
      <p className="title">
        {type === "INCOME" ? "수입" : type === "OUTCOME" ? "지출" : null}{" "}
        기록하기
      </p>
      <CreateForm onSubmit={handleOnSubmit} onCancel={handleOnCancel} />
    </CreatePageStyle>
  );
};

export default CreatePage;
