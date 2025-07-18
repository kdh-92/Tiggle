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
  addTransactionPhotos,
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
  const { mutate: addPhotosMutation } = useMutation(addTransactionPhotos);

  const handleOnSubmit: SubmitHandler<FormInputs> = data => {
    const { date, imageUrls, ...rest } = data;
    const selectedFiles = Array.from(imageUrls);

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
          if (selectedFiles.length > 0) {
            addPhotosMutation(
              {
                transactionId: transactionId!,
                files: selectedFiles,
              },
              {
                onSuccess: () => {
                  messageApi.open({
                    type: "success",
                    content: "거래가 수정되고 새 사진이 추가되었습니다.",
                  });
                  handleSuccessfulUpdate();
                },
                onError: error => {
                  messageApi.open({
                    type: "warning",
                    content: "거래는 수정되었지만 사진 추가에 실패했습니다.",
                  });
                  console.error("사진 추가 실패:", error);
                  handleSuccessfulUpdate();
                },
              },
            );
          } else {
            messageApi.open({
              type: "success",
              content: "거래가 수정되었습니다.",
            });
            handleSuccessfulUpdate();
          }
        },
        onError: error => {
          messageApi.open({
            type: "error",
            content: "거래 수정에 실패했습니다.",
          });
          console.error("거래 수정 실패:", error);
        },
      });
    } else {
      if (selectedFiles.length === 0) {
        messageApi.open({
          type: "error",
          content: "파일을 선택해주세요.",
        });
        return;
      }

      const formData: TransactionFormData = {
        dto: {
          tagNames: data.tags,
          date: dayjs(date).toISOString(),
          ...rest,
        },
        files: selectedFiles,
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
            content: "거래 등록에 실패했습니다.",
          });
          console.error("거래 생성 실패:", error);
        },
      });
    }
  };

  const handleSuccessfulUpdate = () => {
    queryClient.invalidateQueries({
      queryKey: transactionKeys.detail(transactionId!),
    });
    queryClient.invalidateQueries({
      queryKey: transactionKeys.lists(),
    });
    navigate(`/detail/${transactionId}`);
  };

  const handleOnCancel = () => {
    navigate(-1);
  };

  const getDefaultValues = (): Partial<FormInputs> | undefined => {
    if (isEditMode && editTransactionData?.data) {
      const transaction = editTransactionData.data;
      return {
        categoryId: transaction.category?.id,
        amount: transaction.amount,
        content: transaction.content,
        reason: transaction.reason,
        tags: transaction.tagNames || [],
        date: dayjs(transaction.date),
      };
    }
    return undefined;
  };

  const getExistingImageUrls = (): string[] => {
    if (!isEditMode || !editTransactionData?.data?.imageUrls) return [];

    try {
      return typeof editTransactionData.data.imageUrls === "string"
        ? JSON.parse(editTransactionData.data.imageUrls)
        : editTransactionData.data.imageUrls;
    } catch {
      return [];
    }
  };

  return (
    <CreatePageStyle>
      <p className="title">{isEditMode ? "거래 수정하기" : "지출 기록하기"}</p>
      <CreateForm
        key={isEditMode ? `edit-${transactionId}` : "create"}
        onSubmit={handleOnSubmit}
        onCancel={handleOnCancel}
        defaultValues={getDefaultValues()}
        isEditMode={isEditMode}
        transactionId={transactionId || undefined}
        existingImageUrls={getExistingImageUrls()}
      />
    </CreatePageStyle>
  );
};

export default withAuth(CreatePage);
