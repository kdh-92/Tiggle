import { HashTagStyle } from "@/components/atoms/HashTag/HashTagStyle";

interface HashTagProps {
  label: string;
  forwardUrl?: string;
}
export default function HashTag({ label, forwardUrl }: HashTagProps) {
  const renderHashTag = () => (
    <HashTagStyle>
      <span>#</span>
      <span>{label}</span>
    </HashTagStyle>
  );

  return forwardUrl ? (
    <a href={forwardUrl}>{renderHashTag()}</a>
  ) : (
    renderHashTag()
  );
}
