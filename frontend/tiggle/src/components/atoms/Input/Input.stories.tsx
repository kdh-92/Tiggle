import { Meta, StoryObj } from "@storybook/react";

import Input from "./Input";

export default {
  title: "atoms/Input",
  component: Input,
} as Meta<typeof Input>;

type Story = StoryObj<typeof Input>;

export const Default: Story = {
  args: {
    defaultValue: "Hello Input",
    placeholder: "제목을 입력하세요",
    disabled: false,
  },
};
