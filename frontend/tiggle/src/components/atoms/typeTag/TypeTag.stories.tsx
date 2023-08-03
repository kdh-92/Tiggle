import { Meta, StoryObj } from "@storybook/react";

import TypeTag from "./TypeTag";

export default {
  title: "atoms/TypeTag",
  component: TypeTag,
  argTypes: {
    txType: {
      control: "radio",
      options: ["outcome", "refund"],
    },
  },
} as Meta<typeof TypeTag>;

type Story = StoryObj<typeof TypeTag>;

export const Default: Story = {
  args: {
    txType: "outcome",
  },
};
