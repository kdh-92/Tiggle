import { useMemo } from "react";
import { Info } from "react-feather";
import { Controller, SubmitHandler, useForm } from "react-hook-form";

import { Dayjs } from "dayjs";

import {
  CTAButton,
  DatePicker,
  Input,
  MultiSelect,
  Select,
  TextArea,
  TextButton,
  Upload,
} from "@/components/atoms";
import { CreateFormStyle } from "@/pages/CreatePage/CreateForm/CreateFormStyle";
import { TxType } from "@/types";
import { convertTxTypeToWord } from "@/utils/txType";

import {
  useAllAssetsQuery,
  useAllCategoriesQuery,
  useAllTagsQuery,
} from "../query";

export interface FormInputs {
  assetId: number;
  categoryId: number;
  amount: number;
  content: string;
  reason: string;
  tags: Array<string>;
  date: Dayjs;
  imageUrl: FileList;
}

type FormInputsKey = keyof FormInputs;

interface CreateFormProps {
  type: TxType;
  onSubmit: SubmitHandler<FormInputs>;
  onCancel: () => void;
  defaultValues?: Partial<FormInputs>;
  disabledInputs?: Array<FormInputsKey>;
}

function CreateForm({
  type,
  onSubmit,
  onCancel,
  defaultValues,
  disabledInputs,
}: CreateFormProps) {
  const { data: assetsData, isLoading: isAssetsLoading } = useAllAssetsQuery();
  const assets = useMemo(
    () => assetsData?.map(({ name, id }) => ({ value: id, label: name })),
    [assetsData],
  );

  const { data: categoriesData, isLoading: isCategoriesLoading } =
    useAllCategoriesQuery();
  const categories = useMemo(
    () => categoriesData?.map(({ name, id }) => ({ value: id, label: name })),
    [categoriesData],
  );

  const { data: tagsData, isLoading: isTagsLoading } = useAllTagsQuery();
  const tags = useMemo(
    () => tagsData?.map(({ name }) => ({ value: name, label: `#${name}` })),
    [tagsData],
  );

  const { register, control, handleSubmit, resetField } = useForm<FormInputs>({
    defaultValues,
  });

  const handleResetImageUrl = () => {
    resetField("imageUrl");
  };

  return (
    <CreateFormStyle
      className="create-form"
      onSubmit={handleSubmit(onSubmit)}
      encType="multipart/form-data"
    >
      <div className="form-item">
        <label>자산</label>
        <Controller
          name="assetId"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <Select
              placeholder="자산 선택"
              options={assets}
              // TODO: loading ui 추가
              notFoundContent={isAssetsLoading ? <p>loading...</p> : null}
              disabled={disabledInputs?.includes("assetId")}
              {...field}
            />
          )}
        />
      </div>

      <div className="form-item">
        <label>카테고리</label>
        <Controller
          name="categoryId"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <Select
              placeholder="카테고리 선택"
              options={categories}
              // TODO: loading ui 추가
              notFoundContent={isCategoriesLoading ? <p>loading...</p> : null}
              disabled={disabledInputs?.includes("categoryId")}
              {...field}
            />
          )}
        />
      </div>

      <div className="form-divider" />

      <div className="form-item">
        <label>제목</label>
        <Controller
          name="content"
          control={control}
          rules={{ max: 20, required: true }}
          render={({ field }) => (
            <Input
              placeholder="제목을 입력하세요"
              count={{ show: true, max: 20 }}
              disabled={disabledInputs?.includes("content")}
              {...field}
            />
          )}
        />
      </div>

      <div className="form-item">
        <label>금액</label>
        <Controller
          name="amount"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <Input
              placeholder="금액을 입력하세요"
              prefix="₩"
              type="number"
              disabled={disabledInputs?.includes("amount")}
              {...field}
            />
          )}
        />
      </div>

      <div className="form-item">
        <label>{convertTxTypeToWord(type)}일자</label>
        <Controller
          name="date"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <DatePicker
              placeholder="날짜를 입력하세요"
              disabled={disabledInputs?.includes("date")}
              {...field}
            />
          )}
        />
      </div>

      <div className="form-item">
        <label>설명</label>
        <Controller
          name="reason"
          control={control}
          rules={{ required: true, max: 300 }}
          render={({ field }) => (
            <TextArea
              name="reason"
              placeholder={`${convertTxTypeToWord(type)} 이유를 입력하세요.`}
              count={{ show: true, max: 300 }}
              disabled={disabledInputs?.includes("reason")}
              {...field}
            />
          )}
        />
      </div>

      <div className="form-divider" />

      <div className="form-item">
        <label>해시태그</label>
        <Controller
          name="tags"
          control={control}
          rules={{ required: true, max: 5 }}
          render={({ field }) => (
            <MultiSelect
              placeholder="해시태그 선택"
              options={tags}
              // TODO: loading ui 추가
              notFoundContent={isTagsLoading ? <p>loading...</p> : null}
              disabled={disabledInputs?.includes("tags")}
              {...field}
            />
          )}
        />
      </div>

      <div className="form-item">
        <label>사진</label>
        <Upload
          name="imageUrl"
          onReset={handleResetImageUrl}
          disabled={disabledInputs?.includes("imageUrl")}
          {...register("imageUrl", { required: true })}
        />
        <div className="form-item-caption">
          <Info size={12} />
          <p>
            {convertTxTypeToWord(type)}을 증빙할 수 있는 사진을 업로드 해주세요.
          </p>
        </div>
      </div>

      <div className="form-controller">
        <CTAButton type="submit" size="lg" fullWidth>
          등록하기
        </CTAButton>
        <TextButton color="bluishGray500" onClick={onCancel}>
          취소
        </TextButton>
      </div>
    </CreateFormStyle>
  );
}

export default CreateForm;
