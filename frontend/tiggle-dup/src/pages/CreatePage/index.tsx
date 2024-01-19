import { SubmitHandler } from "react-hook-form";
import {
  useNavigate,
  useParams,
  LoaderFunctionArgs,
  useLoaderData,
} from "react-router-dom";

import { QueryClient, useMutation, useQuery } from "@tanstack/react-query";
import dayjs from "dayjs";

import { TransactionApiControllerService } from "@/generated";
import useMessage from "@/hooks/useMessage";
import CreateForm, {
  FormInputs,
} from "@/pages/CreatePage/CreateForm/CreateForm";
import { CreatePageStyle } from "@/pages/CreatePage/CreatePageStyle";
import { transactionKeys } from "@/query/queryKeys";
import { TxType } from "@/types";
import { convertTxTypeToWord } from "@/utils/txType";
import withAuth, { AuthProps } from "@/utils/withAuth";

import TransactionPreviewCell from "./TransactionPreviewCell/TransactionPreviewCell";
import { createTransaction, TransactionFormData } from "./request";

export const transactionQuery = (id: number) => ({
  queryKey: transactionKeys.detail(id),
  queryFn: async () => TransactionApiControllerService.getTransaction(id),
});

export const createPageLoader =
  (queryClient: QueryClient) =>
  ({ params }: LoaderFunctionArgs) => {
    const parentId = Number(params.id);
    return queryClient.ensureQueryData(transactionQuery(parentId));
  };

interface CreatePageProps extends AuthProps {
  type: TxType;
}

const CreatePage = ({ type }: CreatePageProps) => {
  const navigate = useNavigate();
  const messageApi = useMessage();
  const parentId = Number(useParams().id);

  const initialData = useLoaderData() as Awaited<
    ReturnType<ReturnType<typeof createPageLoader>>
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
      multipartFile: imageUrl.item(0)!,
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
        defaultValues={
          parentTxData
            ? {
                assetId: parentTxData.asset?.id,
                categoryId: parentTxData.category?.id,
              }
            : undefined
        }
      />
    </CreatePageStyle>
  );
};

export default withAuth(CreatePage);
