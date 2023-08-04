import Pencil from "@/assets/pencil.svg";
import { TitleButtonStyle } from "@/styles/components/ButtonStyle";

export default function TitleButton() {
  return (
    <TitleButtonStyle shape="round">
      <Pencil /> <span>기록하기</span>
    </TitleButtonStyle>
  );
}
