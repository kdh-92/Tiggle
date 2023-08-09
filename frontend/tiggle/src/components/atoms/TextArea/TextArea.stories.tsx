import { Meta, StoryObj } from "@storybook/react";

import TextArea from "./TextArea";

export default {
  title: "atoms/TextArea",
  component: TextArea,
  argTypes: {
    disabled: {
      type: "boolean",
    },
  },
} as Meta<typeof TextArea>;

type Story = StoryObj<typeof TextArea>;

export const Default: Story = {
  args: {
    value: "hello",
    placeholder: "댓글 남기기",
  },
};
