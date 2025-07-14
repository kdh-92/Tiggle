import React, { useState, useEffect, forwardRef } from "react";
import { Plus, Trash2, X } from "react-feather";

import cn from "classnames";

import {
  UploadStyle,
  ErrorMessageStyle,
} from "@/components/atoms/Upload/UploadStyle";
import { isDesktop } from "@/styles/util/screen";
import { resizeTransactionImage } from "@/utils/imageResize";

interface EditableImageUploadProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  onReset: () => void;
  error?: {
    type: string;
    message?: string;
  };
  existingImages?: string[];
  onDeleteExistingImage?: (index: number) => void;
  transactionId?: number;
  isEditMode?: boolean;
}

const EditableImageUpload = forwardRef<
  HTMLInputElement,
  EditableImageUploadProps
>(
  (
    {
      name = "editableUpload",
      onChange,
      onReset,
      error,
      existingImages = [],
      onDeleteExistingImage,
      transactionId,
      isEditMode = false,
      ...props
    },
    ref,
  ) => {
    const desktop = isDesktop();

    const [newFiles, setNewFiles] = useState<File[]>([]);
    const [newImageUrls, setNewImageUrls] = useState<string[]>([]);
    const [isResizing, setIsResizing] = useState(false);

    const [displayExistingImages, setDisplayExistingImages] =
      useState<string[]>(existingImages);

    useEffect(() => {
      setDisplayExistingImages(existingImages);
    }, [existingImages]);

    const encodeFilesToImageUrls = (files: File[]) => {
      const urls: string[] = [];

      files.forEach((file, index) => {
        const reader = new FileReader();
        reader.onload = function () {
          urls[index] = reader.result as string;

          if (urls.length === files.length) {
            setNewImageUrls(prev => [...prev, ...urls]);
          }
        };
        reader.readAsDataURL(file);
      });
    };

    const handleUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
      onChange?.(e);
      const uploadedFiles = e.target.files;

      if (uploadedFiles && uploadedFiles.length > 0) {
        setIsResizing(true);

        try {
          const fileArray = Array.from(uploadedFiles);
          const resizedFiles: File[] = [];

          for (const file of fileArray) {
            try {
              const resizedFile = await resizeTransactionImage(file);
              resizedFiles.push(resizedFile);
            } catch (error) {
              console.error(`파일 리사이즈 실패 (${file.name}):`, error);
              resizedFiles.push(file);
            }
          }

          setNewFiles(prev => [...prev, ...resizedFiles]);
          encodeFilesToImageUrls(resizedFiles);
        } finally {
          setIsResizing(false);
        }
      }
    };

    const handleReset = () => {
      onReset?.();
      setNewFiles([]);
      setNewImageUrls([]);
      setDisplayExistingImages(existingImages);
    };

    const removeNewFile = (index: number) => {
      setNewFiles(prev => prev.filter((_, i) => i !== index));
      setNewImageUrls(prev => prev.filter((_, i) => i !== index));
    };

    const removeExistingImage = async (index: number) => {
      if (!isEditMode || !transactionId || !onDeleteExistingImage) return;

      const totalImages = displayExistingImages.length + newFiles.length;
      if (totalImages <= 1) {
        alert("최소 1장의 사진은 유지해야 합니다.");
        return;
      }

      try {
        await onDeleteExistingImage(index);
        setDisplayExistingImages(prev => prev.filter((_, i) => i !== index));
      } catch (error) {
        console.error("이미지 삭제 실패:", error);
        alert("이미지 삭제에 실패했습니다.");
      }
    };

    const hasImages = displayExistingImages.length > 0 || newFiles.length > 0;

    return (
      <>
        <UploadStyle className={cn({ filled: hasImages, error })}>
          {hasImages && (
            <div className="view">
              {displayExistingImages.map((url, index) => (
                <div key={`existing-${index}`} className="image-item existing">
                  <img
                    src={`${import.meta.env.VITE_API_URL}${url}`}
                    alt={`기존 이미지 ${index + 1}`}
                  />
                  {isEditMode && (
                    <button
                      type="button"
                      className="remove-btn existing"
                      onClick={() => removeExistingImage(index)}
                      title="기존 이미지 삭제"
                    >
                      <X size={16} />
                    </button>
                  )}
                  <div className="image-badge">기존</div>
                </div>
              ))}

              {newImageUrls.map((url, index) => (
                <div key={`new-${index}`} className="image-item new">
                  <img src={url} alt={`새 이미지 ${index + 1}`} />
                  <button
                    type="button"
                    className="remove-btn new"
                    onClick={() => removeNewFile(index)}
                    title="새 이미지 삭제"
                  >
                    <Trash2 size={16} />
                  </button>
                  <div className="image-badge new">새로운</div>
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
                disabled={isResizing}
                {...props}
              />
              <div className={hasImages ? "upload-filled" : "upload-empty"}>
                <Plus size={desktop ? 24 : 20} />
                <p>
                  {isResizing ? "처리중..." : hasImages ? "추가" : "업로드"}
                </p>
              </div>
            </label>

            {hasImages && (
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

        {isEditMode && (
          <div
            className="edit-mode-info"
            style={{
              fontSize: "12px",
              color: "#666",
              marginTop: "8px",
              display: "flex",
              alignItems: "center",
              gap: "4px",
            }}
          >
            <div style={{ display: "flex", alignItems: "center", gap: "4px" }}>
              <div
                style={{
                  width: "8px",
                  height: "8px",
                  backgroundColor: "#3b82f6",
                  borderRadius: "2px",
                }}
              ></div>
              <span>기존 이미지</span>
            </div>
            <div
              style={{
                display: "flex",
                alignItems: "center",
                gap: "4px",
                marginLeft: "12px",
              }}
            >
              <div
                style={{
                  width: "8px",
                  height: "8px",
                  backgroundColor: "#10b981",
                  borderRadius: "2px",
                }}
              ></div>
              <span>새로 추가된 이미지</span>
            </div>
          </div>
        )}
      </>
    );
  },
);

export default EditableImageUpload;
