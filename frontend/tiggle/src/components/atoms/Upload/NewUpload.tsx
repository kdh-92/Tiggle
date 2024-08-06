import { ChangeEvent, forwardRef, useState } from "react";

import styled, { css } from "styled-components";

import { expandTypography } from "@/styles/util";

const getBase64 = (file: File): Promise<string> =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result as string);
    reader.onerror = error => reject(error);
  });

interface UploadProps extends React.InputHTMLAttributes<HTMLInputElement> {
  onChange?: (e: ChangeEvent<HTMLInputElement>) => void;
  onReset?: () => void;
  defaultValue?: string;
}

const Upload = forwardRef<HTMLInputElement, UploadProps>(
  (
    { name = "upload", onChange, onReset, defaultValue, ...props }: UploadProps,
    ref,
  ) => {
    const [file, setFile] = useState<File | null>(null);
    const [previewUrl, setPreviewUrl] = useState(defaultValue);

    const handleOnChange = async (e: ChangeEvent<HTMLInputElement>) => {
      onChange?.(e);
      const uploadedFile = e.target.files?.[0];
      if (uploadedFile) {
        setFile(uploadedFile);
        const fileUrl = await getBase64(uploadedFile);
        setPreviewUrl(fileUrl);
      }
    };

    const handleOnDelete = () => {
      onReset?.();
      setFile(null);
      setPreviewUrl(undefined);
    };

    return (
      <UploadWrapperStyle>
        <label>
          <UploadInputStyle
            type="file"
            accept="image/*"
            name={name}
            ref={ref}
            onChange={handleOnChange}
            {...props}
          />
          <UploadButtonStyle>
            {previewUrl && <img src={previewUrl} />}
            {!previewUrl && <p>이미지 업로드</p>}
          </UploadButtonStyle>
        </label>

        <SelectDefaultButtonStyle onClick={handleOnDelete}>
          <p>기본 이미지 선택</p>
        </SelectDefaultButtonStyle>
      </UploadWrapperStyle>
    );
  },
);

export default Upload;

const UploadWrapperStyle = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

const UploadInputStyle = styled.input`
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  border: 0;
  overflow: hidden;
`;

const SelectItemStyle = css`
  width: 100%;
  min-width: 480px;
  height: auto;
  min-height: 56px;
  padding: 16px 0;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 16px;
  ${({ theme }) => expandTypography(theme.typography.body.large.regular)}
  color: ${({ theme }) => theme.color.blue[500].value};
  text-align: center;
`;

const UploadButtonStyle = styled.div`
  ${SelectItemStyle}
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;

  img {
    width: 250px;
    height: 150px;
    border-radius: 6px;
    object-fit: cover;
  }
`;

const SelectDefaultButtonStyle = styled.button`
  ${SelectItemStyle}
`;

const FormImageSelectButton = styled.div<{
  selected?: boolean;
}>`
  width: 100%;
  min-width: 480px;
  height: auto;
  min-height: 56px;
  padding: 16px 0;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 16px;
  ${({ theme }) => expandTypography(theme.typography.body.large.regular)}
  color: ${({ theme }) => theme.color.blue[500].value};

  img {
    width: 250px;
    height: 150px;
    border-radius: 6px;
    object-fit: cover;
  }

  ${({ selected }) =>
    selected &&
    css`
      box-shadow: 0 0 0 1px ${({ theme }) => theme.color.blue[500].value};
    `}
`;
