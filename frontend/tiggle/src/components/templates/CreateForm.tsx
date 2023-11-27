import { Info } from "react-feather";
import { Controller, SubmitHandler, useForm } from "react-hook-form";

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
  AssetApiControllerService,
  CategoryApiControllerService,
  TagApiControllerService,
} from "@/generated";
import { CreateFormStyle } from "@/styles/pages/CreatePageStyle";

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

interface CreateFormProps {
  data?: Partial<FormInputs>;
  parentId?: number;
  onSubmit: SubmitHandler<FormInputs>;
  onCancel: () => void;
}

function CreateForm({ onSubmit, onCancel }: CreateFormProps) {
  const { data: assets, isLoading: isAssetsLoading } = useQuery(
    ["asset", "all"],
    async () =>
      AssetApiControllerService.getAllAsset().then(res =>
        res.map(({ name, id }) => ({ value: id, label: name })),
      ),
  );
  const { data: categories, isLoading: isCategoriesLoading } = useQuery(
    ["category", "all"],
    async () =>
      CategoryApiControllerService.getAllCategory().then(res =>
        res.map(({ name, id }) => ({ value: id, label: name })),
      ),
  );
  const { data: tags, isLoading: isTagsLoading } = useQuery(
    ["tag", "all"],
    async () =>
      TagApiControllerService.getAllDefaultTag().then(res =>
        res.map(({ name }) => ({ value: name, label: `#${name}` })),
      ),
  );

  const { register, control, handleSubmit, resetField } = useForm<FormInputs>();

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
              {...field}
            />
          )}
        />
      </div>

      <div className="form-item">
        <label>지출일자</label>
        <Controller
          name="date"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <DatePicker placeholder="날짜를 입력하세요" {...field} />
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
              placeholder="지출 이유를 입력하세요."
              count={{ show: true, max: 300 }}
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
          {...register("imageUrl", { required: true })}
        />
        <div className="form-item-caption">
          <Info size={12} />
          <p>지출을 증빙할 수 있는 사진을 업로드 해주세요.</p>
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
