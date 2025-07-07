import { SubmitHandler } from "react-hook-form";
import {
  useNavigate,
  useParams,
  LoaderFunctionArgs,
  useLoaderData,
  useLocation,
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
import {
  createTransaction,
  updateTransaction,
  TransactionFormData,
  TransactionUpdateData,
} from "./request";

export const transactionQuery = (id: number) => ({
  queryKey: transactionKeys.detail(id),
  queryFn: async () => TransactionApiControllerService.getTransaction(id),
});

export const createPageLoader =
  (queryClient: QueryClient) =>
  ({ params }: LoaderFunctionArgs) => {
    const parentId = Number(params.id);
    if (parentId) {
      return queryClient.ensureQueryData(transactionQuery(parentId));
    }
    return null;
  };

interface CreatePageProps extends AuthProps {
  type: TxType;
}

const CreatePage = ({ type, profile }: CreatePageProps) => {
  const navigate = useNavigate();
  const messageApi = useMessage();
  const location = useLocation();
  const parentId = Number(useParams().id);

  const isEditMode = location.pathname.includes("/edit/");
  const transactionId = isEditMode ? parentId : null;

  const initialData = useLoaderData() as Awaited<
    ReturnType<ReturnType<typeof createPageLoader>>
  >;

  const { data: editTransactionData } = useQuery({
    ...transactionQuery(transactionId!),
    enabled: isEditMode && !!transactionId,
  });

  const { data: parentTxData } = useQuery({
    ...transactionQuery(parentId),
    initialData,
    enabled: type === "REFUND" && !isEditMode,
  });

  const { mutate } = useMutation(createTransaction);

  const { mutate: updateMutation } = useMutation(updateTransaction);

  const handleOnSubmit: SubmitHandler<FormInputs> = data => {
    const { date, imageUrl, ...rest } = data;

    if (isEditMode) {
      const updateData: TransactionUpdateData = {
        transactionId: transactionId!,
        dto: {
          ...rest,
          date: dayjs(date).toISOString(),
          tagNames: data.tags,
        },
      };

      updateMutation(updateData, {
        onSuccess: () => {
          messageApi.open({
            type: "success",
            content: "거래가 수정되었습니다.",
          });
          navigate(`/detail/${transactionId}`);
        },
        onError: error => {
          messageApi.open({
            type: "error",
            content: "거래 수정에 실패했습니다.",
          });
          console.log(error);
        },
      });
    } else {
      const formData: TransactionFormData = {
        dto: {
          type,
          memberId: profile.id,
          // tagNames: tags?.join(", "),
          date: dayjs(date).toISOString(),
          ...rest,
        },
        multipartFile: imageUrl.item(0)!,
      };

      mutate(formData, {
        onSuccess: () => {
          messageApi.open({
            type: "success",
            content: "거래가 등록되었습니다.",
          });
          navigate(`/`);
        },
        onError: error => {
          messageApi.open({
            type: "error",
            content: "거래가 등록에 실패했습니다.",
          });
          console.log(error);
        },
      });
    }
  };

  const handleOnCancel = () => {
    navigate(-1);
  };

  const getDefaultValues = (): Partial<FormInputs> | undefined => {
    if (isEditMode && editTransactionData?.data) {
      const transaction = editTransactionData.data;
      const defaultValues = {
        categoryId: transaction.category?.id,
        amount: transaction.amount,
        content: transaction.content,
        reason: transaction.reason,
        tags: transaction.tagNames || [],
        date: dayjs(transaction.date),
      };

      // (디버깅 추가) - 실제 데이터 확인
      console.log("🔍 Edit Mode - Transaction Data:", transaction);
      console.log("🔍 Default Values:", defaultValues);
      console.log("🔍 TagNames from backend:", transaction.tagNames);

      return defaultValues;
    }

    if (parentTxData?.data) {
      return {};
    }

    return undefined;
  };

  return (
    <CreatePageStyle>
      <p className="title">
        {isEditMode ? "거래 수정하기" : `${convertTxTypeToWord(type)} 기록하기`}
      </p>
      {parentTxData?.data && <TransactionPreviewCell {...parentTxData.data} />}
      <CreateForm
        key={isEditMode ? `edit-${transactionId}` : "create"} // (수정됨) - key 추가
        type={type}
        onSubmit={handleOnSubmit}
        onCancel={handleOnCancel}
        defaultValues={getDefaultValues()}
      />
    </CreatePageStyle>
  );
};

export default withAuth(CreatePage);
