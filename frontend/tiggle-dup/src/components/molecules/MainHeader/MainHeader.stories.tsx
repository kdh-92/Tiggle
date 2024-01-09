import { Meta, StoryObj } from "@storybook/react";

import MainHeader from "@/components/molecules/MainHeader/MainHeader";

export default {
  title: "molecules/MainHeader",
  component: MainHeader,
} as Meta<typeof MainHeader>;

type Story = StoryObj<typeof MainHeader>;

export const Default: Story = {
  args: {},
};
