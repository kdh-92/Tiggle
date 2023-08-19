import { Meta, StoryObj } from "@storybook/react";

import { Tx } from "@/types";

import TypeTag from "./TypeTag";

export default {
  title: "atoms/TypeTag",
  component: TypeTag,
  argTypes: {
    txType: {
      control: "radio",
      options: Object.keys(Tx),
    },
  },
} as Meta<typeof TypeTag>;

type Story = StoryObj<typeof TypeTag>;

export const Default: Story = {
  args: {
    txType: Tx.Outcome,
  },
};
