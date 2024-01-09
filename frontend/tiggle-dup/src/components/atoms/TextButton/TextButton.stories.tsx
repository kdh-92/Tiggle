import { Meta, StoryObj } from "@storybook/react";

import TextButton from "./TextButton";

export default {
  title: "atoms/TextButton",
  component: TextButton,
} as Meta<typeof TextButton>;

type Story = StoryObj<typeof TextButton>;

export const Default: Story = {
  args: {
    children: "취소하기",
    color: "bluishGray300",
  },
};
