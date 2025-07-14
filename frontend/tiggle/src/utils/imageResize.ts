export const resizeImage = async (
  file: File,
  maxWidth: number,
  maxHeight: number,
  quality: number = 0.8,
): Promise<File> => {
  return new Promise((resolve, reject) => {
    const canvas = document.createElement("canvas");
    const ctx = canvas.getContext("2d");
    const img = new Image();

    if (!ctx) {
      reject(new Error("Canvas context를 생성할 수 없습니다."));
      return;
    }

    img.onload = () => {
      const { width, height } = img;

      const ratio = Math.min(maxWidth / width, maxHeight / height);

      if (ratio >= 1) {
        resolve(file);
        return;
      }

      canvas.width = width * ratio;
      canvas.height = height * ratio;

      ctx.imageSmoothingEnabled = true;
      ctx.imageSmoothingQuality = "high";
      ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

      canvas.toBlob(
        blob => {
          if (!blob) {
            reject(new Error("이미지 변환에 실패했습니다."));
            return;
          }

          const resizedFile = new File([blob], file.name, {
            type: "image/jpeg",
            lastModified: Date.now(),
          });

          resolve(resizedFile);
        },
        "image/jpeg",
        quality,
      );

      URL.revokeObjectURL(img.src);
    };

    img.onerror = () => {
      reject(new Error("이미지 로드에 실패했습니다."));
    };

    img.src = URL.createObjectURL(file);
  });
};

export const resizeProfileImage = (file: File) =>
  resizeImage(file, 400, 400, 0.8);
export const resizeTransactionImage = (file: File) =>
  resizeImage(file, 800, 600, 0.85);
