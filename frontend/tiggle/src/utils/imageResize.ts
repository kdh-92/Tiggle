const resizeImage = (
  file: File,
  maxWidth: number,
  maxHeight: number,
  quality: number = 0.85,
): Promise<File> => {
  return new Promise((resolve, reject) => {
    const canvas = document.createElement("canvas");
    const ctx = canvas.getContext("2d");
    const img = new Image();
    let objectUrl: string | null = null;

    if (!ctx) {
      if (objectUrl) URL.revokeObjectURL(objectUrl);
      reject(new Error("Canvas context is not available"));
      return;
    }

    img.onload = () => {
      const { width: originalWidth, height: originalHeight } = img;

      let newWidth = originalWidth;
      let newHeight = originalHeight;

      if (originalWidth > maxWidth || originalHeight > maxHeight) {
        const widthRatio = maxWidth / originalWidth;
        const heightRatio = maxHeight / originalHeight;
        const ratio = Math.min(widthRatio, heightRatio);

        newWidth = originalWidth * ratio;
        newHeight = originalHeight * ratio;
      }

      canvas.width = newWidth;
      canvas.height = newHeight;

      ctx.drawImage(img, 0, 0, newWidth, newHeight);

      canvas.toBlob(
        blob => {
          if (objectUrl) {
            URL.revokeObjectURL(objectUrl);
          }

          if (blob) {
            const resizedFile = new File([blob], file.name, {
              type: file.type,
              lastModified: Date.now(),
            });
            resolve(resizedFile);
          } else {
            reject(new Error("Canvas toBlob failed"));
          }
        },
        file.type,
        quality,
      );
    };

    img.onerror = () => {
      if (objectUrl) {
        URL.revokeObjectURL(objectUrl);
      }
      reject(new Error("Image load failed"));
    };

    objectUrl = URL.createObjectURL(file);
    img.src = objectUrl;
  });
};

/**
 * Transaction 이미지 리사이즈 (800x600)
 */
export const resizeTransactionImage = (file: File): Promise<File> => {
  return resizeImage(file, 800, 600, 0.85);
};

/**
 * Member 프로필 이미지 리사이즈 (400x400)
 */
export const resizeMemberImage = (file: File): Promise<File> => {
  return resizeImage(file, 400, 400, 0.8);
};
