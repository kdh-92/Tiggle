import { Meta, StoryObj } from "@storybook/react";

import ReplyToggleButton, {
  ReplyToggleButtonProps,
} from "@/pages/DetailPage/ReplyToggleButton/ReplyToggleButton";
import { Tx, TxType } from "@/types";

type ReplyToggleButtonPropsWithTxType = ReplyToggleButtonProps & {
  txType: TxType;
};

export default {
  title: "atoms/ReplyToggleButton",
  component: ReplyToggleButton,
  render: ({ txType, ...args }) => {
    return <ReplyToggleButton {...args} />;
  },
  argTypes: {
    txType: {
      options: Object.values(Tx),
      control: { type: "radio" },
    },
  },
} as Meta<ReplyToggleButtonPropsWithTxType>;

type Story = StoryObj<ReplyToggleButtonPropsWithTxType>;

export const Default: Story = {
  args: {
    txType: Tx.REFUND,
    repliesCount: 2,
    open: true,
  },
};
