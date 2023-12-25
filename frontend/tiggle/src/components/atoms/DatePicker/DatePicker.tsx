import { forwardRef } from "react";

import { DatePickerProps as AntDatePickerProps } from "antd";

import { DatePickerStyle } from "@/components/atoms/DatePicker/DatePickerStyle";
import { isDesktop } from "@/styles/util/screen";

type DatePickerProps = AntDatePickerProps;
type DatePickerRef = React.Ref<any>;

const DatePicker = forwardRef(
  ({ ...props }: DatePickerProps, ref: DatePickerRef) => {
    const desktop = isDesktop();
    return (
      <DatePickerStyle
        ref={ref}
        size={desktop ? "large" : "middle"}
        {...props}
      />
    );
  },
);

export default DatePicker;
