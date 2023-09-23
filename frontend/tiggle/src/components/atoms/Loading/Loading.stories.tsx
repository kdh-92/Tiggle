import { Meta, StoryObj } from "@storybook/react";

import Loading from "./Loading";

export default {
  title: "atoms/Loading",
  component: Loading,
} as Meta<typeof Loading>;

type Story = StoryObj<typeof Loading>;

export const Default: Story = {
  args: {},
};
