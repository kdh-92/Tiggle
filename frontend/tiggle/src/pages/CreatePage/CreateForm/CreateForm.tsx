import { useMemo } from "react";
import { Info } from "react-feather";
import {
  Controller,
  FieldError,
  SubmitHandler,
  useForm,
} from "react-hook-form";

import { useQuery } from "@tanstack/react-query";
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
import {
  // AssetApiControllerService,
  CategoryApiControllerService,
  TagApiControllerService,
} from "@/generated";
import { CreateFormStyle } from "@/pages/CreatePage/CreateForm/CreateFormStyle";
// import { tagKeys } from "@/query/queryKeys";
// import { assetKeys, categoryKeys, tagKeys } from "@/query/queryKeys";
import { categoryKeys, tagKeys } from "@/query/queryKeys";
import { TxType } from "@/types";
import { convertTxTypeToWord } from "@/utils/txType";

export interface FormInputs {
  // assetId: number;
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
  disabledInputs?: FormInputsKey[];
}

function CreateForm({
  type,
  onSubmit,
  onCancel,
  defaultValues,
  disabledInputs,
}: CreateFormProps) {
  // const { data: assetsData, isLoading: isAssetsLoading } = useQuery(
  //   assetKeys.lists(),
  //   async () => AssetApiControllerService.getAllAsset(),
  // );
  const { data: categoriesData, isLoading: isCategoriesLoading } = useQuery(
    categoryKeys.lists(),
    async () => CategoryApiControllerService.getAllCategory(),
  );
  const { data: tagsData, isLoading: isTagsLoading } = useQuery(
    tagKeys.lists(),
    async () => TagApiControllerService.getAllDefaultTag(),
  );

  // const assets = useMemo(
  //   () => assetsData?.map(({ name, id }) => ({ value: id, label: name })),
  //   [assetsData],
  // );
  const categories = useMemo(
    () =>
      categoriesData?.categories?.map(({ name, id }) => ({
        value: id,
        label: name,
      })),
    [categoriesData],
  );
  const tags = useMemo(
    () => tagsData?.map(({ name }) => ({ value: name, label: `#${name}` })),
    [tagsData],
  );

  const {
    register,
    control,
    handleSubmit,
    resetField,
    formState: { isValid, errors },
  } = useForm<FormInputs>({
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
      {/*<div className="form-item">*/}
      {/*  <label>자산</label>*/}
      {/*  <Controller*/}
      {/*    name="assetId"*/}
      {/*    control={control}*/}
      {/*    rules={{ required: "자산을 선택해 주세요." }}*/}
      {/*    render={({ field }) => (*/}
      {/*      <Select*/}
      {/*        placeholder="자산 선택"*/}
      {/*        options={assets}*/}
      {/*        // TODO: loading ui 추가*/}
      {/*        notFoundContent={isAssetsLoading ? <p>loading...</p> : null}*/}
      {/*        disabled={disabledInputs?.includes("assetId")}*/}
      {/*        error={errors.assetId}*/}
      {/*        {...field}*/}
      {/*      />*/}
      {/*    )}*/}
      {/*  />*/}
      {/*</div>*/}

      <div className="form-item">
        <label>카테고리</label>
        <Controller
          name="categoryId"
          control={control}
          rules={{ required: "카테고리를 선택해 주세요." }}
          render={({ field }) => (
            <Select
              placeholder="카테고리 선택"
              options={categories}
              // TODO: loading ui 추가
              notFoundContent={isCategoriesLoading ? <p>loading...</p> : null}
              disabled={disabledInputs?.includes("categoryId")}
              error={errors.categoryId}
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
          rules={{
            max: { value: 20, message: "20자 이내로 작성해 주세요." },
            required: "제목을 입력해 주세요.",
          }}
          render={({ field }) => (
            <Input
              placeholder="제목을 입력하세요"
              count={{ show: true, max: 20 }}
              disabled={disabledInputs?.includes("content")}
              error={errors.content}
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
          rules={{ required: "금액을 입력해 주세요." }}
          render={({ field }) => (
            <Input
              placeholder="금액을 입력하세요"
              prefix="₩"
              type="number"
              disabled={disabledInputs?.includes("amount")}
              error={errors.amount}
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
          rules={{ required: "날짜를 입력해 주세요." }}
          render={({ field }) => (
            <DatePicker
              placeholder="날짜를 입력하세요"
              disabled={disabledInputs?.includes("date")}
              error={errors.date as FieldError}
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
          rules={{
            required: "설명을 입력해 주세요.",
            max: { value: 300, message: "300자 이내로 입력해 주세요." },
          }}
          render={({ field }) => (
            <TextArea
              placeholder={`${convertTxTypeToWord(type)} 이유를 입력하세요.`}
              count={{ show: true, max: 300 }}
              disabled={disabledInputs?.includes("reason")}
              error={errors.reason}
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
          render={({ field }) => (
            <MultiSelect
              placeholder="해시태그 선택"
              options={tags}
              // TODO: loading ui 추가
              notFoundContent={isTagsLoading ? <p>loading...</p> : null}
              disabled={disabledInputs?.includes("tags")}
              error={errors.tags as FieldError}
              {...field}
            />
          )}
        />
      </div>

      <div className="form-item">
        <label>사진</label>
        <Upload
          onReset={handleResetImageUrl}
          disabled={disabledInputs?.includes("imageUrl")}
          {...register("imageUrl", { required: "사진을 업로드 해주세요" })}
          error={errors.imageUrl}
        />
        <div className="form-item-caption">
          <Info size={12} />
          <p>
            {convertTxTypeToWord(type)}을 증빙할 수 있는 사진을 업로드 해주세요.
          </p>
        </div>
      </div>

      <div className="form-controller">
        <CTAButton type="submit" size="lg" fullWidth disabled={!isValid}>
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
