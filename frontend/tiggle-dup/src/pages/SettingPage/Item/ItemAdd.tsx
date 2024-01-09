import { PlusCircle } from "react-feather";

import { ItemAddStyle } from "./ItemStyle";

interface ItemAddProps {
  onClick: () => void;
}

const ItemAdd = ({ onClick }: ItemAddProps) => {
  return (
    <ItemAddStyle onClick={onClick}>
      <PlusCircle />
      <p>새 자산 추가하기</p>
    </ItemAddStyle>
  );
};

export default ItemAdd;
