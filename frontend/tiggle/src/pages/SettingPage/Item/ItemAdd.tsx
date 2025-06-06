import { PlusCircle } from "react-feather";

import { ItemAddStyle } from "./ItemStyle";

interface ItemAddProps {
  onClick: () => void;
}

const ItemAdd = ({ onClick }: ItemAddProps) => {
  return (
    <ItemAddStyle onClick={onClick}>
      <PlusCircle />
      <p>새 카테고리 추가하기</p>
    </ItemAddStyle>
  );
};

export default ItemAdd;
