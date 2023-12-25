import { forwardRef } from "react";
import { ChevronDown } from "react-feather";

import { SelectProps as AntSelectProps, Select as AntSelect } from "antd";
import cn from "classnames";

import { SelectStyle } from "@/components/atoms/Select/SelectStyle";
import { isDesktop } from "@/styles/util/screen";

interface SelectProps extends AntSelectProps {
  variant?: "default" | "compact";
}

type SelectRef = Parameters<typeof AntSelect>[0]["ref"];

const Select = forwardRef(
  (
    { variant = "default", className, ...props }: SelectProps,
    ref: SelectRef,
  ) => {
    const desktop = isDesktop();

    return (
      <SelectStyle
        ref={ref}
        className={cn(className, variant)}
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

export default Select;
