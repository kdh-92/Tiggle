import { forwardRef } from "react";
import { ChevronDown } from "react-feather";

import { Select as AntSelect } from "antd";

import { MultiSelectStyle } from "@/components/atoms/MultiSelect/MultiSelectStyle";
import { isDesktop } from "@/styles/util/screen";

type AntSelectProps = Parameters<typeof AntSelect>[0];
type SelectRef = AntSelectProps["ref"];

interface MultiSelectProps extends AntSelectProps {}

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
