import { SettingFormStyle } from "./SettingFormStyle";
import { SettingFormProps } from "./types";
import useSettingForm from "./useSettingForm";
import Item from "../Item/Item";
import ItemAdd from "../Item/ItemAdd";

const SettingForm = (props: SettingFormProps) => {
  const { fields, appendItem, updateItem, removeItem } = useSettingForm(props);

  return (
    <SettingFormStyle>
      {fields.map((field, index) => (
        <Item
          key={`setting-item-${index}`}
          value={field}
          save={(name: string) => updateItem(index, { ...field, name })}
          deleteSelf={() => removeItem(index, field.sid)}
          defaultStatus={field.sid === -1 ? "edit" : "display"}
        />
      ))}
      <ItemAdd onClick={appendItem} />
    </SettingFormStyle>
  );
};

export default SettingForm;
