import { forwardRef } from "react";

import { InputProps as AntInputProps, Input as AntInput } from "antd";

import {
  ErrorMessageStyle,
  InputStyle,
} from "@/components/atoms/Input/InputStyle";
import { isDesktop } from "@/styles/util/screen";

interface InputProps extends AntInputProps {
  error?: {
    type: string;
    message?: string;
  };
}
type InputRef = Parameters<typeof AntInput>[0]["ref"];

const Input = forwardRef(({ error, ...props }: InputProps, ref: InputRef) => {
  const desktop = isDesktop();

  return (
    <>
      <InputStyle
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
});

export default Input;
