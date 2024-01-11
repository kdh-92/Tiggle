export interface SettingItem {
  /**
   * server에서 부여되는 id
   * React-hook-form에서 부여되는 client측 id와 구분하기 위해
   * sid로 기재
   */
  sid: number;
  name: string;
}

export type SettingInput<FieldName extends string> = {
  [F in FieldName]: SettingItem[];
};

export type Data = {
  id: number;
  name: string;
};

export interface SettingFormProps {
  data: Array<SettingItem>;
  requests: {
    create: (value: string, callback?: (data: Data) => void) => void;
    update: (
      sid: number,
      value: string,
      callback?: (data: Data) => void,
    ) => void;
    remove: (sid: number, callback?: (data: Data) => void) => void;
  };
}
