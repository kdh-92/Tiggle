import { ChangeEvent, ChangeEventHandler, useState } from "react";

import { encodeFilesToDataUrls } from "@/utils/fileReader";
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

  const handleUpload = async (e: ChangeEvent<HTMLInputElement>) => {
    onChange?.(e);
    const uploadedFiles = e.target.files;

    if (uploadedFiles && uploadedFiles.length > 0) {
      const fileArray = Array.from(uploadedFiles);
      const resizedFiles: File[] = [];

      for (const file of fileArray) {
        try {
          const resizedFile = await resizeTransactionImage(file);
          resizedFiles.push(resizedFile);
        } catch (error) {
          console.error(`리사이즈 실패: ${file.name}`, error);
          resizedFiles.push(file);
        }
      }

      setFiles(prev => [...prev, ...resizedFiles]);

      const dataTransfer = new DataTransfer();
      [...files, ...resizedFiles].forEach(file => dataTransfer.items.add(file));

      if (e.target) {
        e.target.files = dataTransfer.files;
      }

      try {
        const urls = await encodeFilesToDataUrls(dataTransfer.files);
        setImageUrls(prev => [...prev, ...urls]);
      } catch (error) {
        console.error("파일 읽기 실패:", error);
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
  };
};

export default useMultiUpload;
