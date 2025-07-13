import { SubmitHandler } from "react-hook-form";
import {
  useNavigate,
  useParams,
  LoaderFunctionArgs,
  useLocation,
} from "react-router-dom";

import {
  QueryClient,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import dayjs from "dayjs";

import { TransactionApiControllerService } from "@/generated";
import useMessage from "@/hooks/useMessage";
import CreateForm, {
  FormInputs,
} from "@/pages/CreatePage/CreateForm/CreateForm";
import { CreatePageStyle } from "@/pages/CreatePage/CreatePageStyle";
import { transactionKeys } from "@/query/queryKeys";
import withAuth from "@/utils/withAuth";

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

const CreatePage = () => {
  const navigate = useNavigate();
  const messageApi = useMessage();
  const location = useLocation();
  const parentId = Number(useParams().id);
  const queryClient = useQueryClient();

  const isEditMode = /^\/create\/edit\/\d+\/?$/.test(location.pathname);
  const transactionId = isEditMode ? parentId : null;

  const { data: editTransactionData } = useQuery({
    ...transactionQuery(transactionId!),
    enabled: isEditMode && !!transactionId,
  });

  const { mutate } = useMutation(createTransaction);

  const { mutate: updateMutation } = useMutation(updateTransaction);

  const handleOnSubmit: SubmitHandler<FormInputs> = data => {
    const { date, imageUrl, ...rest } = data;
    const selectedFile = imageUrl.item(0);

    if (!selectedFile) {
      messageApi.open({
        type: "error",
        content: "파일을 선택해주세요.",
      });
      return;
    }

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

          queryClient.invalidateQueries({
            queryKey: transactionKeys.detail(transactionId!),
          });
          queryClient.invalidateQueries({
            queryKey: transactionKeys.lists(),
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
          tagNames: data.tags,
          date: dayjs(date).toISOString(),
          ...rest,
        },
        multipartFile: selectedFile,
      };

      mutate(formData, {
        onSuccess: () => {
          messageApi.open({
            type: "success",
            content: "거래가 등록되었습니다.",
          });

          queryClient.invalidateQueries({
            queryKey: transactionKeys.lists(),
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

      return defaultValues;
    }

    return undefined;
  };

  return (
    <CreatePageStyle>
      <p className="title">{isEditMode ? "거래 수정하기" : "지출 기록하기"}</p>
      <CreateForm
        key={isEditMode ? `edit-${transactionId}` : "create"}
        onSubmit={handleOnSubmit}
        onCancel={handleOnCancel}
        defaultValues={getDefaultValues()}
      />
    </CreatePageStyle>
  );
};

export default withAuth(CreatePage);
