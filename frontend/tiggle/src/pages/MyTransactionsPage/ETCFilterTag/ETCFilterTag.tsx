import { X } from "react-feather";
import { useFormContext } from "react-hook-form";

import { ETCFilterTagStyle } from "./ETCFilterTagStyle";
import { FilterInputs } from "../types";

interface ETCFilterTagProps {
  label: string;
  value: number | string;
  keyName: keyof Pick<FilterInputs, "assetIds" | "categoryIds" | "tagNames">;
}

const ETCFilterTag = ({ label, value, keyName }: ETCFilterTagProps) => {
  const { getValues, setValue } = useFormContext<FilterInputs>();

  const deleteTag = () => {
    if (keyName === "tagNames") {
      const filteredValues = getValues(keyName).filter(v => v !== value);
      setValue(keyName, filteredValues);
    } else {
      const filteredValues = getValues(keyName).filter(v => v !== value);
      setValue(keyName, filteredValues);
    }
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
