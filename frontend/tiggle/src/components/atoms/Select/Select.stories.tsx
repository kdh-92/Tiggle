import { Meta, StoryObj } from "@storybook/react";

import Select from "./Select";

export default {
  title: "atoms/Select",
  component: Select,
} as Meta<typeof Select>;

type Story = StoryObj<typeof Select>;

export const Default: Story = {
  args: {
    placeholder: "선택하기",
    options: [
      { value: "jack", label: "Jack" },
      { value: "lucy", label: "Lucy" },
      { value: "Yiminghe", label: "yiminghe" },
      { value: "disabled", label: "Disabled", disabled: true },
    ],
    disabled: true,
  },
};
