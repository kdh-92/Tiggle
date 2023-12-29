import { useEffect } from "react";
import { useFieldArray, useForm } from "react-hook-form";

import { ItemWrapperStyle } from "./AssetSettingPageStyle";
import Item from "./Item/Item";
import ItemAdd from "./ItemAdd/ItemAdd";
import { useAssetsQuery } from "./query";
import { MypageDetailPageStyle } from "../MyProfilePage/MyDetailPageCommonStyle";

interface AssetSettingPageProps {}

const fieldArrayName = "assetList";
export type AssetsInput = {
  [fieldArrayName]: {
    sid: number;
    label: string;
  }[];
};

const AssetSettingPage = ({}: AssetSettingPageProps) => {
  const { data: assetsData } = useAssetsQuery();

  const { control, setValue } = useForm<AssetsInput>({
    defaultValues: { [fieldArrayName]: [] },
  });
  const { fields, append, update, remove } = useFieldArray({
    control,
    name: fieldArrayName,
  });

  const appendField = () => {
    append({ sid: -1, label: "" });
  };

  useEffect(() => {
    if (assetsData) {
      setValue(
        fieldArrayName,
        assetsData.map(({ id, name }) => ({ sid: id, label: name })),
      );
    }
  }, [assetsData]);

  return (
    <MypageDetailPageStyle>
      <p className="page-title">내 자산 관리</p>

      <ItemWrapperStyle>
        {fields.map((field, index) => (
          <Item
            key={`asset-item-${field.id}`}
            index={index}
            value={field}
            updateField={update}
            removeField={remove}
            defaultStatus={field.label === "" ? "edit" : "display"}
          />
        ))}
        <ItemAdd onClick={appendField} />
      </ItemWrapperStyle>
    </MypageDetailPageStyle>
  );
};

export default AssetSettingPage;
