import { forwardRef } from "react";
import { FieldError } from "react-hook-form";

import { DatePickerProps as AntDatePickerProps } from "antd";

import {
  DatePickerStyle,
  ErrorMessageStyle,
} from "@/components/atoms/DatePicker/DatePickerStyle";
import { isDesktop } from "@/styles/util/screen";

type DatePickerProps = AntDatePickerProps & {
  error?: FieldError;
};
type DatePickerRef = React.Ref<any>;

const DatePicker = forwardRef(
  ({ error, ...props }: DatePickerProps, ref: DatePickerRef) => {
    const desktop = isDesktop();

    return (
      <>
        <DatePickerStyle
          ref={ref}
          size={desktop ? "large" : "middle"}
          status={error ? "error" : undefined}
          {...props}
        />
        {error && (
          <ErrorMessageStyle>{error.message ?? error.type}</ErrorMessageStyle>
        )}
      </>
    );
  },
);

export default DatePicker;
