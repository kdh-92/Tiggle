import { useFieldArray, useForm } from "react-hook-form";

import { SettingInput, SettingFormProps, SettingItem } from "./types";

const fieldArrayName = "items";

const useSettingForm = ({ data, requests }: SettingFormProps) => {
  const { control } = useForm<SettingInput<typeof fieldArrayName>>({
    defaultValues: { [fieldArrayName]: data },
  });

  const { fields, append, update, remove } = useFieldArray({
    control,
    name: fieldArrayName,
  });

  const appendItem = () => {
    append({ sid: -1, name: "" });
  };

  const updateItem = (index: number, item: SettingItem) => {
    if (item.sid === -1) {
      requests.create(item.name, data => {
        update(index, { sid: data.id, name: item.name });
      });
    } else {
      requests.update(item.sid, item.name, () => {
        update(index, item);
      });
    }
  };

  const removeItem = (index: number, sid: number) => {
    requests.remove(sid, () => {
      remove(index);
    });
  };

  return {
    fields,
    appendItem,
    updateItem,
    removeItem,
  };
};

export default useSettingForm;
