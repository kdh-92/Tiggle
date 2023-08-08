import { useState } from "react";

import { Meta, StoryObj } from "@storybook/react";

import { Tx } from "@/types";

import ReactionButton from "./ReactionButton";

export default {
  title: "atoms/ReactionButton",
  component: ReactionButton,
} as Meta<typeof ReactionButton>;

type Story = StoryObj<typeof ReactionButton>;

export const Default: Story = {
  args: {
    tx: Tx.Outcome,
    reaction: "good",
    number: 964,
  },
};

export const WithToggle: Story = {
  args: {
    tx: Tx.Outcome,
    reaction: "good",
    number: 144,
  },
  render: args => {
    const [checked, setChecked] = useState(false);
    const toggle = () => {
      setChecked(!checked);
    };
    return <ReactionButton {...args} checked={checked} onClick={toggle} />;
  },
};
