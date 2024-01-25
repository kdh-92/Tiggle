import { useEffect } from "react";
import { useDispatch } from "react-redux";

import { Meta, StoryObj } from "@storybook/react";

import ReplyToggleButton, {
  ReplyToggleButtonProps,
} from "@/pages/DetailPage/ReplyToggleButton/ReplyToggleButton";
import detailPageStore from "@/store/detailPage";
import { Tx, TxType } from "@/types";

type ReplyToggleButtonPropsWithTxType = ReplyToggleButtonProps & {
  txType: TxType;
};

export default {
  title: "atoms/ReplyToggleButton",
  component: ReplyToggleButton,
  render: ({ txType, ...args }) => {
    const dispatch = useDispatch();
    useEffect(() => {
      dispatch(detailPageStore.actions.setType(txType));
    }, [txType]);
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
