import { useCallback, useState } from "react";
import { UseFieldArrayRemove, UseFieldArrayUpdate } from "react-hook-form";

import { AssetsInput } from "../AssetSettingPage";
import ItemDisplay from "../ItemDisplay/ItemDisplay";
import ItemEdit from "../ItemEdit/ItemEdit";

type ItemStatus = "display" | "edit";

interface ItemProps {
  index: number;
  value: {
    sid: number;
    label: string;
  };
  updateField: UseFieldArrayUpdate<AssetsInput>;
  removeField: UseFieldArrayRemove;
  defaultStatus?: ItemStatus;
}

const Item = ({
  index,
  value,
  updateField,
  removeField,
  defaultStatus,
}: ItemProps) => {
  const [status, setStatus] = useState<ItemStatus>(defaultStatus || "display");

  const edit = useCallback(() => setStatus("edit"), [status]);
  const cancel = useCallback(() => setStatus("display"), [status]);
  const save = (newValue: string) => {
    if (value.sid === -1) {
      // TODO: Asset Create API 연결
      console.log("create asset");
    } else {
      // TODO: Asset Update API 연결
      console.log("update asset");
    }
    updateField(index, { ...value, label: newValue });
  };
  const remove = () => {
    // TODO: alert
    removeField(index);
    // TODO: Asset Delete API 연결
  };

  return status === "edit" ? (
    <ItemEdit label={value.label} onCancel={cancel} onSave={save} />
  ) : (
    <ItemDisplay label={value.label} onEdit={edit} onRemove={remove} />
  );
};

export default Item;
