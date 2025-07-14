import { ChangeEvent, ChangeEventHandler, useState } from "react";

import { resizeTransactionImage } from "@/utils/imageResize";

type UseMultiUploadOptions = {
  onChange: ChangeEventHandler<HTMLInputElement>;
  onReset: () => void;
};

const useMultiUpload = ({
  onChange,
  onReset,
}: Partial<UseMultiUploadOptions>) => {
  const [files, setFiles] = useState<File[]>([]);
  const [imageUrls, setImageUrls] = useState<string[]>([]);
  const [isResizing, setIsResizing] = useState(false);

  const encodeFilesToImageUrls = (newFiles: File[]) => {
    const newUrls: string[] = [];

    newFiles.forEach((file, index) => {
      const reader = new FileReader();
      reader.onload = function () {
        newUrls[index] = reader.result as string;

        if (newUrls.length === newFiles.length) {
          setImageUrls(prev => [...prev, ...newUrls]);
        }
      };
      reader.readAsDataURL(file);
    });
  };

  const handleUpload = async (e: ChangeEvent<HTMLInputElement>) => {
    onChange?.(e);
    const uploadedFiles = e.target.files;

    if (uploadedFiles && uploadedFiles.length > 0) {
      setIsResizing(true);

      try {
        const fileArray = Array.from(uploadedFiles);
        const resizedFiles: File[] = [];

        // 각 파일을 순차적으로 리사이즈
        for (const file of fileArray) {
          try {
            const resizedFile = await resizeTransactionImage(file);
            resizedFiles.push(resizedFile);
          } catch (error) {
            console.error(`파일 리사이즈 실패 (${file.name}):`, error);
            resizedFiles.push(file);
          }
        }

        setFiles(prev => [...prev, ...resizedFiles]);
        encodeFilesToImageUrls(resizedFiles);
      } finally {
        setIsResizing(false);
      }
    }
  };

  const handleReset = () => {
    onReset?.();
    setFiles([]);
    setImageUrls([]);
  };

  const removeFile = (index: number) => {
    setFiles(prev => prev.filter((_, i) => i !== index));
    setImageUrls(prev => prev.filter((_, i) => i !== index));
  };

  return {
    files,
    imageUrls,
    handleUpload,
    handleReset,
    removeFile,
    isResizing,
  };
};

export default useMultiUpload;
