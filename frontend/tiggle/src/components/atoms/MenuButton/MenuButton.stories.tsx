import { Meta, StoryObj } from "@storybook/react";

import MenuButton from "@/components/atoms/MenuButton/MenuButton";

export default {
  title: "atoms/MenuButton",
  component: MenuButton,
} as Meta<typeof MenuButton>;

type Story = StoryObj<typeof MenuButton>;

export const Default: Story = {
  args: {},
};
