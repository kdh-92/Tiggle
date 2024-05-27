import { forwardRef } from "react";
import { ChevronDown } from "react-feather";

import { Select as AntSelect } from "antd";
import cn from "classnames";

import {
  ErrorMessageStyle,
  SelectStyle,
} from "@/components/atoms/Select/SelectStyle";
import { isDesktop } from "@/styles/util/screen";

type AntSelectProps = Parameters<typeof AntSelect>[0];
type SelectRef = AntSelectProps["ref"];

interface SelectProps extends Omit<AntSelectProps, "variant"> {
  variant?: "default" | "compact";
  error?: {
    type: string;
    message?: string;
  };
}

const Select = forwardRef(
  (
    { variant = "default", className, error, ...props }: SelectProps,
    ref: SelectRef,
  ) => {
    const desktop = isDesktop();

    return (
      <>
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

export default Select;
