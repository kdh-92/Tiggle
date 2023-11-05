import { InputProps as AntInputProps } from "antd";

import { InputStyle } from "@/styles/components/InputStyle";
import { isDesktop } from "@/styles/util/screen";

interface InputProps extends AntInputProps {}

const Input = ({ ...props }: InputProps) => {
  const desktop = isDesktop();

  return <InputStyle size={desktop ? "large" : "middle"} {...props} />;
};

export default Input;
