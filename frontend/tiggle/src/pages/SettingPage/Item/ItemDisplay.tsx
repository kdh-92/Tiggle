import { ItemStyle } from "./ItemStyle";

interface ItemDisplayProps {
  label: string;
  onEdit: () => void;
  onDelete: () => void;
}

const ItemDisplay = ({ label, onEdit, onDelete }: ItemDisplayProps) => {
  return (
    <ItemStyle>
      <p className="label">{label}</p>
      <div className="controllers">
        <button onClick={onEdit}>수정</button>
        <button onClick={onDelete}>삭제</button>
      </div>
    </ItemStyle>
  );
};

export default ItemDisplay;
