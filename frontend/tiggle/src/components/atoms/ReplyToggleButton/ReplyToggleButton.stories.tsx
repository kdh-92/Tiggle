import { Meta, StoryObj } from "@storybook/react";

import ReplyToggleButton from "@/components/atoms/ReplyToggleButton/ReplyToggleButton";
import { Tx } from "@/types";

export default {
  title: "atoms/ReplyToggleButton",
  component: ReplyToggleButton,
} as Meta<typeof ReplyToggleButton>;

type Story = StoryObj<typeof ReplyToggleButton>;

export const Default: Story = {
  args: {
    txType: Tx.OUTCOME,
    repliesCount: 2,
    open: true,
  },
};
