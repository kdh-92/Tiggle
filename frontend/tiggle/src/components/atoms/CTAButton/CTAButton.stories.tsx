import { Edit3 } from "react-feather";

import { Meta, StoryObj } from "@storybook/react";

import CTAButton from "./CTAButton";

export default {
  title: "atoms/CTAButton",
  component: CTAButton,
} as Meta<typeof CTAButton>;

type Story = StoryObj<typeof CTAButton>;

export const Default: Story = {
  args: {
    size: "md",
    children: "기록하기",
    icon: <Edit3 />,
    disabled: false,
  },
};
