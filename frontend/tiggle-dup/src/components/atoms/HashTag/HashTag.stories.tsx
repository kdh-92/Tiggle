import { Meta, StoryObj } from "@storybook/react";

import HashTag from "@/components/atoms/HashTag/HashTag";

export default {
  title: "atoms/HashTag",
  component: HashTag,
} as Meta<typeof HashTag>;

type Story = StoryObj<typeof HashTag>;

export const Default: Story = {
  args: {
    label: "카페인중독",
    forwardUrl: "/?path=/story/atoms-ctabutton--default",
  },
};
