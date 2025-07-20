export const getProfileImageUrl = (
  profileUrl: string | null | undefined,
): string | undefined => {
  if (!profileUrl) return undefined;

  if (profileUrl.startsWith("http")) {
    return profileUrl;
  }

  const apiUrl = import.meta.env.VITE_API_URL;
  if (!apiUrl) {
    console.error("VITE_API_URL 환경변수가 설정되지 않았습니다.");
    return undefined;
  }

  const baseUrl = apiUrl.replace(/\/$/, "");
  return `${baseUrl}/${profileUrl}`;
};
