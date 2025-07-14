import { ChangeEvent, ChangeEventHandler, useState } from "react";

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

  const encodeFilesToImageUrls = (fileList: FileList) => {
    const newFiles = Array.from(fileList);
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

  const handleUpload = (e: ChangeEvent<HTMLInputElement>) => {
    onChange?.(e);
    const uploadedFiles = e.target.files;
    if (uploadedFiles && uploadedFiles.length > 0) {
      const newFiles = Array.from(uploadedFiles);
      setFiles(prev => [...prev, ...newFiles]);
      encodeFilesToImageUrls(uploadedFiles);
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
