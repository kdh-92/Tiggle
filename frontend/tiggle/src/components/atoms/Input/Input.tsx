import { forwardRef } from "react";

import { InputProps as AntInputProps, Input as AntInput } from "antd";

import { InputStyle } from "@/styles/components/InputStyle";
import { isDesktop } from "@/styles/util/screen";

interface InputProps extends AntInputProps {}
type InputRef = Parameters<typeof AntInput>[0]["ref"];

const Input = forwardRef(({ ...props }: InputProps, ref: InputRef) => {
  const desktop = isDesktop();

  return (
    <InputStyle ref={ref} size={desktop ? "large" : "middle"} {...props} />
  );
});

export default Input;
