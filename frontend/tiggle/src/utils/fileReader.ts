export const encodeFilesToDataUrls = (
  fileList: FileList,
): Promise<string[]> => {
  const files = Array.from(fileList);

  return Promise.all(
    files.map(file => {
      return new Promise<string>((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result as string);
        reader.onerror = () =>
          reject(new Error(`파일 읽기 실패: ${file.name}`));
        reader.readAsDataURL(file);
      });
    }),
  );
};
