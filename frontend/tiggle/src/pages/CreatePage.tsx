import { Info } from "react-feather";
import { Controller, SubmitHandler, useForm } from "react-hook-form";

import DatePicker from "@/components/atoms/DatePicker/DatePicker";
import Input from "@/components/atoms/Input/Input";
import Select from "@/components/atoms/Select/Select";
import { TransactionDto } from "@/generated";
import {
  CreatePageStyle,
  CreateFormStyle,
} from "@/styles/pages/CreatePageStyle";

import type { Dayjs } from "dayjs";

type CreateInputs = Required<
  Omit<TransactionDto, "memberId" | "parentId" | "type" | "date">
> & {
  date: Dayjs;
};

const CreatePage = () => {
  const { control, handleSubmit } = useForm<CreateInputs>();

  const handleOnSubmit: SubmitHandler<CreateInputs> = data => {
    console.log(data);
  };

  return (
    <CreatePageStyle>
      <p className="title">지출 기록하기</p>
      <CreateFormStyle
        className="create-form"
        onSubmit={handleSubmit(handleOnSubmit)}
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
              <Input placeholder="제목을 입력하세요" {...field} />
            )}
          />
        </div>

        <div className="form-item">
          <label>금액</label>
          <Controller
            name="reason"
            control={control}
            render={({ field }) => (
              <Input placeholder="금액을 입력하세요" prefix="₩" {...field} />
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
              <Input
                className="reason"
                placeholder="지출 이유를 입력하세요."
                {...field}
              />
            )}
          />
        </div>

        <div className="form-divider" />

        <div className="form-item">
          <label>해시태그</label>
          <Input />
        </div>

        <div className="form-item">
          <label>사진</label>
          <Input />
          <div className="form-item-caption">
            <Info size={12} />
            <p>지출을 증빙할 수 있는 사진을 업로드 해주세요.</p>
          </div>
        </div>
      </CreateFormStyle>
    </CreatePageStyle>
  );
};

export default CreatePage;
