import { Meta, StoryObj } from "@storybook/react";

import LoginHeader from "@/components/molecules/LoginHeader/LoginHeader";

export default {
  title: "molecules/LoginHeader",
  component: LoginHeader,
} as Meta<typeof LoginHeader>;

type Story = StoryObj<typeof LoginHeader>;

export const Default: Story = {
  args: {},
};
