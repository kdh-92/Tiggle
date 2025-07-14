import { forwardRef, useMemo } from "react";
import { Plus, Trash2 } from "react-feather";

import cn from "classnames";

import {
  UploadStyle,
  ErrorMessageStyle,
} from "@/components/atoms/Upload/UploadStyle";
import { isDesktop } from "@/styles/util/screen";

import useMultiUpload from "./useMultiUpload";

interface MultiUploadProps extends React.InputHTMLAttributes<HTMLInputElement> {
  onReset: () => void;
  error?: {
    type: string;
    message?: string;
  };
}

const MultiUpload = forwardRef<HTMLInputElement, MultiUploadProps>(
  ({ name = "multiUpload", onChange, onReset, error, ...props }, ref) => {
    const desktop = isDesktop();

    const { files, imageUrls, handleUpload, handleReset, removeFile } =
      useMultiUpload({
        onChange,
        onReset,
      });

    const filled = useMemo(() => files.length > 0, [files]);

    return (
      <>
        <UploadStyle className={cn({ filled, error })}>
          {filled && (
            <div className="view">
              {imageUrls.map((url, index) => (
                <div key={index} className="image-item">
                  <img src={url} alt={`preview ${index}`} />
                  <button
                    type="button"
                    className="remove-btn"
                    onClick={() => removeFile(index)}
                  >
                    <Trash2 size={16} />
                  </button>
                </div>
              ))}
            </div>
          )}

          <div className="controller">
            <label className="upload">
              <input
                type="file"
                accept="image/*"
                multiple
                ref={ref}
                name={name}
                onChange={handleUpload}
                {...props}
              />
              <div className="upload-area">
                <Plus size={desktop ? 24 : 20} />
                <p>{files.length > 0 ? "사진 추가" : "사진 업로드"}</p>
              </div>
            </label>

            {filled && (
              <button type="button" className="reset" onClick={handleReset}>
                <Trash2 size={desktop ? 20 : 16} strokeWidth={1.5} />
                <p>전체 삭제</p>
              </button>
            )}
          </div>
        </UploadStyle>
        {error && (
          <ErrorMessageStyle>{error.message ?? error.type}</ErrorMessageStyle>
        )}
      </>
    );
  },
);

export default MultiUpload;
