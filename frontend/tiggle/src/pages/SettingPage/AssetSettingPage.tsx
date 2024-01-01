import { useMutation } from "@tanstack/react-query";

import { Loading } from "@/components/atoms";
import { AssetApiControllerService } from "@/generated";

import SettingForm from "./SettingForm/SettingForm";
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
  const { data: assetsData, isLoading } = useAssetsQuery();

  const { mutate } = useMutation(async (name: string) =>
    AssetApiControllerService.createAsset({ name }),
  );

  const create = (value: string) => {
    mutate(value);
    console.log(`ASSET PAGE - create api called`, value);
  };
  const update = (sid: number, newValue: string) => {
    // TODO: API 연결
    console.log(`ASSET PAGE - update api called`, sid, newValue);
  };
  const remove = (sid: number) => {
    // TODO: API 연결
    console.log(`ASSET PAGE - remove api called`, sid);
  };

  return (
    <MypageDetailPageStyle>
      <p className="page-title">내 자산 관리</p>

      {isLoading && <Loading />}
      {!isLoading && assetsData && (
        <SettingForm
          data={assetsData.map(({ id, name }) => ({ sid: id, name }))}
          requests={{ create, update, remove }}
        />
      )}
    </MypageDetailPageStyle>
  );
};

export default AssetSettingPage;
