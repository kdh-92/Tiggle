import { useEffect, useRef } from "react";

import { ItemEditInputStyle, ItemStyle } from "./ItemStyle";

interface ItemEditProps {
  label: string;
  onSave: (newValue: string) => void;
  onCancel: () => void;
}

const ItemEdit = ({ label, onSave, onCancel }: ItemEditProps) => {
  const inputRef = useRef<HTMLInputElement | null>(null);

  const handleSave = () => {
    const { value } = inputRef.current;
    onSave(value);
  };

  useEffect(() => {
    if (inputRef?.current) {
      inputRef.current.focus();
    }
  }, [inputRef?.current]);

  return (
    <ItemStyle>
      <ItemEditInputStyle
        ref={inputRef}
        className="label"
        defaultValue={label}
        placeholder="자산을 입력하세요"
      />
      <div className="controllers">
        <button onClick={onCancel}>취소</button>
        <button onClick={handleSave}>저장</button>
      </div>
    </ItemStyle>
  );
};

export default ItemEdit;
