import { useCallback, useState } from "react";

import ItemDisplay from "./ItemDisplay";
import ItemEdit from "./ItemEdit";
import { SettingItem } from "../SettingForm/types";

type ItemStatus = "display" | "edit";

interface ItemProps {
  value: SettingItem;
  save: (newValue: string) => void;
  deleteSelf: () => void;
  defaultStatus?: ItemStatus;
}

const Item = ({ value, save, deleteSelf, defaultStatus }: ItemProps) => {
  const [status, setStatus] = useState<ItemStatus>(defaultStatus || "display");

  const handleEdit = useCallback(() => setStatus("edit"), [status]);

  const handleSave = useCallback(
    (newValue: string) => {
      save(newValue);
      setStatus("display");
    },
    [status, save],
  );

  const handleCancel = useCallback(() => {
    if (value.sid === -1) {
      deleteSelf();
    } else {
      setStatus("display");
    }
  }, [status, value, deleteSelf]);

  const handleDelete = useCallback(() => {
    // TODO: open alert 로직 추가
    deleteSelf();
  }, [deleteSelf]);

  return status === "edit" ? (
    <ItemEdit label={value.name} onCancel={handleCancel} onSave={handleSave} />
  ) : (
    <ItemDisplay
      label={value.name}
      onEdit={handleEdit}
      onDelete={handleDelete}
    />
  );
};

export default Item;
