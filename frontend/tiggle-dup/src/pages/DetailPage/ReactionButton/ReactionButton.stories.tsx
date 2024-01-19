import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";

import { Meta, StoryObj } from "@storybook/react";

import detailPageStore from "@/store/detailPage";
import { Tx, TxType } from "@/types";

import ReactionButton, { ReactionButtonProps } from "./ReactionButton";

type ReactionButtonPropsWithTxType = ReactionButtonProps & { txType: TxType };

export default {
  title: "atoms/ReactionButton",
  component: ReactionButton,
  render: ({ txType, ...args }) => {
    const dispatch = useDispatch();
    const [checked, setChecked] = useState(false);
    const toggle = () => {
      setChecked(!checked);
    };
    useEffect(() => {
      dispatch(detailPageStore.actions.setType(txType));
    }, [txType]);
    return <ReactionButton {...args} checked={checked} onClick={toggle} />;
  },
  argTypes: {
    txType: {
      options: Object.values(Tx),
      control: { type: "radio" },
    },
  },
} as Meta<ReactionButtonPropsWithTxType>;

type Story = StoryObj<ReactionButtonPropsWithTxType>;

export const Default: Story = {
  args: {
    reaction: "UP",
    number: 964,
    txType: Tx.REFUND,
  },
};
