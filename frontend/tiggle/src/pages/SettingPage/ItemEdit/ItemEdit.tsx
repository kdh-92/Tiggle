import { useEffect, useRef } from "react";

import { ItemEditInputStyle, ItemStyle } from "../AssetSettingPageStyle";

interface ItemEditProps {
  label: string;
  onCancel: () => void;
  onSave: (newValue: string) => void;
}

const ItemEdit = ({ label, onCancel, onSave }: ItemEditProps) => {
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
