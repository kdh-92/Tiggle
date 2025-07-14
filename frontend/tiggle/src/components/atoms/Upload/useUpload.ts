import { ChangeEvent, ChangeEventHandler, useState } from "react";

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

  const handleUpload = (e: ChangeEvent<HTMLInputElement>) => {
    onChange?.(e);
    const uploadedFile = e.target.files?.[0];
    if (uploadedFile) {
      setFile(uploadedFile);
      encodeFileToImageUrl(uploadedFile);
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
