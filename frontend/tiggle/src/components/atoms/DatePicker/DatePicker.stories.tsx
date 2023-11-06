import { Meta, StoryObj } from "@storybook/react";

import DatePicker from "./DatePicker";

export default {
  title: "atoms/DatePicker",
  component: DatePicker,
} as Meta<typeof DatePicker>;

type Story = StoryObj<typeof DatePicker>;

export const Default: Story = {
  args: {
    disabled: true,
  },
};
