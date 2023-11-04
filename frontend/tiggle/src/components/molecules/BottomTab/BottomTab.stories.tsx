import { Meta, StoryObj } from "@storybook/react";

import BottomTab from "@/components/molecules/BottomTab/BottomTab";

export default {
  title: "molecules/BottomTab",
  component: BottomTab,
} as Meta<typeof BottomTab>;

type Story = StoryObj<typeof BottomTab>;

export const Default: Story = {
  args: {},
};
