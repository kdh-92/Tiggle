import { DatePickerProps as AntDatePickerProps } from "antd";

import { DatePickerStyle } from "@/styles/components/DatePickerStyle";
import { isDesktop } from "@/styles/util/screen";

type DatePickerProps = AntDatePickerProps & {
  variant: "default" | "filled";
};

const DatePicker = ({ ...props }: DatePickerProps) => {
  const desktop = isDesktop();
  return <DatePickerStyle size={desktop ? "large" : "middle"} {...props} />;
};

export default DatePicker;
