import { Meta, StoryObj } from "@storybook/react";

import MultiSelect from "./MultiSelect";

export default {
  title: "atoms/MultiSelect",
  component: MultiSelect,
} as Meta<typeof MultiSelect>;

type Story = StoryObj<typeof MultiSelect>;

export const Default: Story = {
  args: {
    placeholder: "해시태그 선택",
    options: [
      { value: 1, label: "해시태그 1" },
      { value: 2, label: "해시태그 2" },
      { value: 3, label: "해시태그 3" },
    ],
    defaultValue: [
      { value: 1, label: "해시태그 1" },
      { value: 2, label: "해시태그 2" },
    ],
    disabled: false,
  },
};
