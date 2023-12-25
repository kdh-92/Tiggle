import { ChangeEvent, forwardRef, useMemo, useState } from "react";
import { Plus, Trash2, Upload as UploadIcon } from "react-feather";

import cn from "classnames";

import { UploadStyle } from "@/components/atoms/Upload/UploadStyle";
import { isDesktop } from "@/styles/util/screen";

interface UploadProps extends React.InputHTMLAttributes<HTMLInputElement> {
  onReset: () => void;
}

const Upload = forwardRef<HTMLInputElement, UploadProps>(
  ({ name = "upload", onChange, onReset, ...props }, ref) => {
    const desktop = isDesktop();

    const [file, setFile] = useState<File | null>(null);
    const [imageUrl, setImageUrl] = useState("");
    const filled = useMemo(() => file !== null, [file]);

    const encodeFileToImageUrl = (file: File) => {
      const reader = new FileReader();
      reader.onload = function () {
        setImageUrl(reader.result as string);
      };
      reader.readAsDataURL(file);
    };

    const handleUpload = (e: ChangeEvent<HTMLInputElement>) => {
      onChange(e);
      const uploadedFile = e.target.files?.[0];
      if (uploadedFile) {
        setFile(uploadedFile);
        encodeFileToImageUrl(uploadedFile);
      }
    };

    const handleReset = () => {
      onReset();
      setFile(null);
      setImageUrl("");
    };

    return (
      <UploadStyle className={cn({ filled })}>
        {filled && (
          <div className="view">
            <img src={imageUrl} alt={`preview of file ${file.name}`} />
          </div>
        )}

        <div className="controller">
          <label className="upload">
            <input
              type="file"
              accept="image/*"
              ref={ref}
              name={name}
              onChange={handleUpload}
              {...props}
            />
            {file ? (
              <div className="upload-filled">
                <UploadIcon size={desktop ? 20 : 16} strokeWidth={1.5} />
                <p>다시 업로드</p>
              </div>
            ) : (
              <div className="upload-empty">
                <Plus size={desktop ? 24 : 20} />
                <p>사진 업로드</p>
              </div>
            )}
          </label>

          {filled && (
            <button className="reset" onClick={handleReset}>
              <Trash2 size={desktop ? 20 : 16} strokeWidth={1.5} />
              <p>삭제하기</p>
            </button>
          )}
        </div>
      </UploadStyle>
    );
  },
);

export default Upload;
