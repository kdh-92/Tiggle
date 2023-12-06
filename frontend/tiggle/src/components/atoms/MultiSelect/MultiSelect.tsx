import { forwardRef } from "react";
import { ChevronDown } from "react-feather";

import { Select as AntSelect, SelectProps as AntSelectProps } from "antd";

import { MultiSelectStyle } from "@/styles/components/MultiSelectStyle";
import { isDesktop } from "@/styles/util/screen";

interface MultiSelectProps extends AntSelectProps {}
type SelectRef = Parameters<typeof AntSelect>[0]["ref"];

const MultiSelect = forwardRef(
  ({ ...props }: MultiSelectProps, ref: SelectRef) => {
    const desktop = isDesktop();

    return (
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
        {...props}
      />
    );
  },
);

export default MultiSelect;
