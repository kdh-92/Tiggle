import { ChangeEvent, ChangeEventHandler, useState } from "react";

import { resizeProfileImage } from "@/utils/imageResize";

type UseUploadOptions = {
  onChange: ChangeEventHandler<HTMLInputElement>;
  onReset: () => void;
  defaultUrl: string;
};

const useUpload = ({
  onChange,
  onReset,
  defaultUrl,
}: Partial<UseUploadOptions>) => {
  const [file, setFile] = useState<File | null>(null);
  const [imageUrl, setImageUrl] = useState(defaultUrl ?? "");
  const [isResizing, setIsResizing] = useState(false);

  const encodeFileToImageUrl = (file: File) => {
    const reader = new FileReader();
    reader.onload = function () {
      setImageUrl(reader.result as string);
    };
    reader.readAsDataURL(file);
  };

  const handleUpload = async (e: ChangeEvent<HTMLInputElement>) => {
    onChange?.(e);
    const uploadedFile = e.target.files?.[0];

    if (uploadedFile) {
      setIsResizing(true);

      try {
        const resizedFile = await resizeProfileImage(uploadedFile);

        setFile(resizedFile);
        encodeFileToImageUrl(resizedFile);
      } catch (error) {
        console.error("이미지 리사이즈 실패:", error);
        setFile(uploadedFile);
        encodeFileToImageUrl(uploadedFile);
      } finally {
        setIsResizing(false);
      }
    }
  };

  const handleReset = () => {
    onReset?.();
    setFile(null);
    setImageUrl("");
  };

  return {
    file,
    imageUrl,
    handleUpload,
    handleReset,
    isResizing,
  };
};

export default useUpload;
