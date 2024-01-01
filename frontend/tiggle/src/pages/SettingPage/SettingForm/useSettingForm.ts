import { useFieldArray, useForm } from "react-hook-form";

import { SettingFormProps } from "./SettingForm";

export interface SettingItem {
  /**
   * server에서 부여되는 id
   * React-hook-form에서 부여되는 client측 id와 구분하기 위해
   * sid로 기재
   */
  sid: number;
  name: string;
}

const fieldArrayName = "items";
interface SettingInput {
  [fieldArrayName]: SettingItem[];
}

const useSettingForm = ({ data, requests }: SettingFormProps) => {
  const { control } = useForm<SettingInput>({
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
    update(index, item);

    if (item.sid === -1) {
      requests.create(item.name);
    } else {
      requests.update(item.sid, item.name);
    }
  };

  const removeItem = (index: number, sid: number) => {
    remove(index);
    requests.remove(sid);
  };

  return {
    fields,
    appendItem,
    updateItem,
    removeItem,
  };
};

export default useSettingForm;
