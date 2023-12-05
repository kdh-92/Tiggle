import { SubmitHandler } from "react-hook-form";
import {
  useNavigate,
  useParams,
  LoaderFunctionArgs,
  useLoaderData,
} from "react-router-dom";

import { QueryClient, useMutation, useQuery } from "@tanstack/react-query";
import cn from "classnames";
import dayjs from "dayjs";

import { TypeTag } from "@/components/atoms";
import CreateForm, { FormInputs } from "@/components/templates/CreateForm";
import {
  TransactionApiControllerService,
  TransactionRespDto,
} from "@/generated";
import {
  CreatePageStyle,
  TransactionPreviewCellStyle,
} from "@/styles/pages/CreatePageStyle";
import { useMessage } from "@/templates/GeneralTemplate";
import { TxType } from "@/types";
import { convertTxTypeToWord } from "@/utils/txType";

import { createTransaction, TransactionFormData } from "./request";

const transactionQuery = (id: number) => ({
  queryKey: ["transaction", "detail", id],
  queryFn: async () => TransactionApiControllerService.getTransaction(id),
});

export const loader =
  (queryClient: QueryClient) =>
  ({ params }: LoaderFunctionArgs) => {
    const parentId = Number(params.id);
    return isNaN(parentId)
      ? undefined
      : queryClient.ensureQueryData(transactionQuery(parentId));
  };

interface CreatePageProps {
  type: TxType;
}

const CreatePage = ({ type }: CreatePageProps) => {
  const navigate = useNavigate();
  const { messageApi } = useMessage();
  const parentId = Number(useParams().id);

  const initialData = useLoaderData() as Awaited<
    ReturnType<ReturnType<typeof loader>>
  >;

  const { data: parentTxData } = useQuery({
    ...transactionQuery(parentId),
    initialData,
    enabled: type === "REFUND",
  });

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
      <p className="title">{convertTxTypeToWord(type)} 기록하기</p>
      {parentTxData && <TransactionPreviewCell {...parentTxData} />}
      <CreateForm
        type={type}
        onSubmit={handleOnSubmit}
        onCancel={handleOnCancel}
        disabledInputs={
          type === "REFUND" ? ["assetId", "categoryId"] : undefined
        }
        // TODO: parentTxData의 assetId, categoryId 전달
        defaultValues={parentTxData ? { assetId: 1, categoryId: 1 } : undefined}
      />
    </CreatePageStyle>
  );
};

export default CreatePage;

interface TransactionPreviewCellProps
  extends Pick<TransactionRespDto, "type" | "content" | "reason" | "amount"> {}

const TransactionPreviewCell = ({
  type,
  amount,
  content,
  reason,
}: TransactionPreviewCellProps) => {
  return (
    <TransactionPreviewCellStyle>
      <p className="cell-label">원본 {convertTxTypeToWord(type)}</p>
      <div className="cell-container">
        <div className="cell-contents-wrapper">
          <TypeTag txType={type} size={"md"} />
          <p className={cn("amount", type)}>₩ {amount}</p>
        </div>
        <div className="cell-contents-wrapper">
          <p className="content">{content}</p>
          <p className="reason">{reason}</p>
        </div>
      </div>
    </TransactionPreviewCellStyle>
  );
};
