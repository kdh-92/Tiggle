import {
  Controller,
  FieldError,
  SubmitHandler,
  useForm,
} from "react-hook-form";
import {
  useNavigate,
  useParams,
  LoaderFunctionArgs,
  useLoaderData,
} from "react-router-dom";

import { QueryClient, useMutation, useQuery } from "@tanstack/react-query";
import dayjs, { Dayjs } from "dayjs";

import {
  CTAButton,
  DatePicker,
  Input,
  MultiSelect,
  NewUpload,
  Select,
  TextArea,
} from "@/components/atoms";
import { TransactionApiControllerService } from "@/generated";
import useMessage from "@/hooks/useMessage";
import CreateForm from "@/pages/CreatePage/CreateForm/CreateForm";
import { transactionKeys } from "@/query/queryKeys";
import { TxType } from "@/types";
import { convertTxTypeToWord } from "@/utils/txType";
import { AuthProps } from "@/utils/withAuth";

import { CreatePageStyle, CreateFormStyle2 } from "./CreatePageStyle";
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

interface CreatePageLegacyProps extends AuthProps {
  type: TxType;
}

export const CreatePageLegacy = ({ type, profile }: CreatePageLegacyProps) => {
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
        memberId: profile.id,
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
        defaultValues={parentTxData ? {} : undefined}
      />
    </CreatePageStyle>
  );
};

interface FormInputs {
  assetId: number;
  categoryId: number;
  amount: number;
  content: string;
  reason: string;
  tags: Array<string>;
  date: Dayjs;
  imageUrl: FileList;
}

interface CreatePageProps {}

const CreatePage = ({ ...props }: CreatePageProps) => {
  const {
    register,
    control,
    handleSubmit,
    resetField,
    formState: { isValid, errors },
  } = useForm<FormInputs>({
    defaultValues: {
      date: dayjs(),
    },
  });

  return (
    <CreatePageStyle>
      <p className="title">기록 작성하기</p>

      <CreateFormStyle2
        onChange={e => e.preventDefault()}
        onSubmit={e => e.preventDefault()}
      >
        <div className="form-item">
          <label>작성일자</label>
          <Controller
            name="date"
            control={control}
            rules={{ required: "날짜를 입력해 주세요." }}
            render={({ field }) => (
              <DatePicker
                placeholder="날짜를 입력하세요"
                error={errors.date as FieldError}
                {...field}
              />
            )}
          />
        </div>

        <div className="form-dialog">
          <label>오늘 하루 무지출 하셨나요?</label>
          <div className="form-dialog-controllers">
            <CTAButton size="xl" color="bluishGray" variant="light" fullWidth>
              네
            </CTAButton>
            <CTAButton size="xl" color="bluishGray" variant="light" fullWidth>
              아니요
            </CTAButton>
          </div>
        </div>

        <div className="form-dialog">
          <label htmlFor="create-title">어떤 것에 지출하셨나요?</label>
          <Input id="create-title" placeholder="제목을 입력해 주세요." />
        </div>

        <div className="form-dialog">
          <label htmlFor="create-content">
            왜 구매하셨나요?
            <br />
            지출 이유를 알려주세요.
          </label>
          <TextArea
            id="create-content"
            placeholder="지출 이유를 입력해 주세요."
          />
        </div>

        <div className="form-dialog">
          <label htmlFor="create-amount">
            얼마인가요?
            <br />
            지출 금액을 알려주세요.
          </label>
          <Input placeholder="금액을 입력하세요." prefix="₩" type="number" />
        </div>

        <div className="form-dialog">
          <label htmlFor="create-category">
            지출의 카테고리를 입력해주세요.
          </label>
          <Select
            id="create-category"
            placeholder="카테고리를 선택해 주세요."
            options={[]}
          />
        </div>

        <div className="form-dialog">
          <label htmlFor="create-hashtag">
            지출의 해시태그를 입력해주세요.
          </label>
          <MultiSelect
            id="create-hashtag"
            placeholder="해시태그 선택해 주세요."
            options={[]}
          />
        </div>

        <div className="form-dialog">
          <label htmlFor="create-image">
            지출과 관련된 사진을 첨부해 주세요.
          </label>
          <div className="form-dialog-controllers">
            <NewUpload />
          </div>
        </div>

        <div className="form-submit">
          <CTAButton type="submit" size="lg" fullWidth>
            등록하기
          </CTAButton>
        </div>
      </CreateFormStyle2>
    </CreatePageStyle>
  );
};

export default CreatePage;
