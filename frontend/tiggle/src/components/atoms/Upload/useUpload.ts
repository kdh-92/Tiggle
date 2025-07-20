import { ChangeEvent, ChangeEventHandler, useState } from "react";

import { resizeMemberImage } from "@/utils/imageResize";

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
      try {
        const resizedFile = await resizeMemberImage(uploadedFile);
        setFile(resizedFile);
        encodeFileToImageUrl(resizedFile);

        const dataTransfer = new DataTransfer();
        dataTransfer.items.add(resizedFile);
        if (e.target) {
          e.target.files = dataTransfer.files;
        }
      } catch (error) {
        console.error("Member 프로필 리사이즈 실패:", error);
        setFile(uploadedFile);
        encodeFileToImageUrl(uploadedFile);
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
  };
};

export default useUpload;
