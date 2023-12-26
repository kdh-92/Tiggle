import { X } from "react-feather";
import { useFormContext } from "react-hook-form";

import { ETCFilterTagStyle } from "./ETCFilterTagStyle";
import { FilterInputs } from "../types";

interface ETCFilterTagProps {
  label: string;
  value: number;
  keyName: keyof Pick<FilterInputs, "assetIds" | "categoryIds" | "tagIds">;
}

const ETCFilterTag = ({ label, value, keyName }: ETCFilterTagProps) => {
  const { getValues, setValue } = useFormContext<FilterInputs>();

  const deleteTag = () => {
    const values = getValues(keyName);
    const filteredValues = values.filter(_value => _value !== value);
    console.log(values);
    setValue(keyName, filteredValues);
  };

  return (
    <ETCFilterTagStyle>
      <p>{label}</p>
      <button>
        <X onClick={deleteTag} />
      </button>
    </ETCFilterTagStyle>
  );
};

export default ETCFilterTag;
