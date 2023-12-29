import { ItemStyle } from "../AssetSettingPageStyle";

interface ItemDisplayProps {
  label: string;
  onEdit: () => void;
  onRemove: () => void;
}

const ItemDisplay = ({ label, onEdit, onRemove }: ItemDisplayProps) => {
  return (
    <ItemStyle>
      <p className="label">{label}</p>
      <div className="controllers">
        <button onClick={onEdit}>수정</button>
        <button onClick={onRemove}>삭제</button>
      </div>
    </ItemStyle>
  );
};

export default ItemDisplay;
