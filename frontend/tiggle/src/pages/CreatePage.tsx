import { Info } from "react-feather";
import { Controller, SubmitHandler, useForm } from "react-hook-form";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import DatePicker from "@/components/atoms/DatePicker/DatePicker";
import Input from "@/components/atoms/Input/Input";
import MultiSelect from "@/components/atoms/MultiSelect/MultiSelect";
import Select from "@/components/atoms/Select/Select";
import TextArea from "@/components/atoms/TextArea/TextArea";
import TextButton from "@/components/atoms/TextButton/TextButton";
import Upload from "@/components/atoms/Upload/Upload";
import {
  CreatePageStyle,
  CreateFormStyle,
} from "@/styles/pages/CreatePageStyle";

import type { Dayjs } from "dayjs";

type CreateInputs = {
  assetId: number;
  categoryId: number;
  amount: number;
  content: string;
  reason: string;
  tags: Array<string>;
  date: Dayjs;
  imageUrl: FileList;
};

const CreatePage = () => {
  const { register, control, handleSubmit, resetField } =
    useForm<CreateInputs>();

  const handleOnSubmit: SubmitHandler<CreateInputs> = data => {
    console.log(data);
  };

  const handleOnCancel = () => {
    console.log("cancel");
  };

  const handleResetImageUrl = () => {
    resetField("imageUrl");
  };

  return (
    <CreatePageStyle>
      <p className="title">지출 기록하기</p>
      <CreateFormStyle
        className="create-form"
        onSubmit={handleSubmit(handleOnSubmit)}
        encType="multipart/form-data"
      >
        <div className="form-item">
          <label>자산</label>
          <Controller
            name="assetId"
            control={control}
            render={({ field }) => (
              <Select
                placeholder="자산 선택"
                options={[{ label: "자산1", value: 1 }]}
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
            render={({ field }) => (
              <Select
                placeholder="카테고리 선택"
                options={[{ label: "카테고리1", value: 1 }]}
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
            render={({ field }) => (
              <MultiSelect
                placeholder="해시태그 선택"
                options={[
                  { label: "해시태그1", value: "hashtag-1" },
                  { label: "해시태그2", value: "hashtag-2" },
                  { label: "해시태그3", value: "hashtag-3" },
                ]}
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
            {...register("imageUrl")}
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
          <TextButton color="bluishGray500" onClick={handleOnCancel}>
            취소
          </TextButton>
        </div>
      </CreateFormStyle>
    </CreatePageStyle>
  );
};

export default CreatePage;
