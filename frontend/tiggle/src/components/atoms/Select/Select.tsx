import { forwardRef } from "react";
import { ChevronDown } from "react-feather";

import { SelectProps as AntSelectProps, Grid, Select as AntSelect } from "antd";
import cn from "classnames";

import { SelectStyle } from "@/styles/components/SelectStyle";

const { useBreakpoint } = Grid;

interface SelectProps extends AntSelectProps {
  variant?: "default" | "compact";
}

type SelectRef = Parameters<typeof AntSelect>[0]["ref"];

const Select = forwardRef(
  (
    { variant = "default", className, ...props }: SelectProps,
    ref: SelectRef,
  ) => {
    const screens = useBreakpoint();
    const isDesktop = Object.entries(screens)
      .filter(([, v]) => !!v)
      .map(([k]) => k)
      .includes("lg");

    return (
      <SelectStyle
        ref={ref}
        className={cn(className, variant)}
        size={isDesktop ? "large" : "middle"}
        suffixIcon={
          <ChevronDown
            className="icon"
            strokeWidth={1.5}
            size={isDesktop ? 24 : 20}
          />
        }
        popupMatchSelectWidth
        {...props}
      />
    );
  },
);

export default Select;
