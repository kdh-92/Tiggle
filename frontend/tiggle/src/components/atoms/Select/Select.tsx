import { ChevronDown } from "react-feather";

import { SelectProps as AntSelectProps, Grid } from "antd";
import cn from "classnames";

import { SelectStyle } from "@/styles/components/SelectStyle";

const { useBreakpoint } = Grid;

interface SelectProps extends AntSelectProps {
  variant: "default" | "compact";
}

const Select = ({ variant = "default", className, ...props }: SelectProps) => {
  const screens = useBreakpoint();
  const isDesktop = Object.entries(screens)
    .filter(([, v]) => !!v)
    .map(([k]) => k)
    .includes("lg");

  return (
    <SelectStyle
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
};

export default Select;
