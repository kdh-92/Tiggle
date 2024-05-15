import { forwardRef } from "react";
import { ChevronDown } from "react-feather";
import { FieldError } from "react-hook-form";

import { Select as AntSelect } from "antd";

import {
  ErrorMessageStyle,
  MultiSelectStyle,
} from "@/components/atoms/MultiSelect/MultiSelectStyle";
import { isDesktop } from "@/styles/util/screen";

type AntSelectProps = Parameters<typeof AntSelect>[0] & {
  error?: FieldError;
};
type SelectRef = AntSelectProps["ref"];

interface MultiSelectProps extends AntSelectProps {}

const MultiSelect = forwardRef(
  ({ error, ...props }: MultiSelectProps, ref: SelectRef) => {
    const desktop = isDesktop();

    return (
      <>
        <MultiSelectStyle
          mode="multiple"
          ref={ref}
          size={desktop ? "large" : "middle"}
          suffixIcon={
            <ChevronDown
              className="icon"
              strokeWidth={1.5}
              size={desktop ? 24 : 20}
            />
          }
          popupMatchSelectWidth
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

export default MultiSelect;
