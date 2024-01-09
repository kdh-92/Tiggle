import { Meta, StoryObj } from "@storybook/react";

import Alert from "./Alert";

export default {
  title: "atoms/Alert",
  component: Alert,
} as Meta<typeof Alert>;

type Story = StoryObj<typeof Alert>;

export const Default: Story = {
  args: {
    isOpen: true,
    onClose: () => console.log("close"),
    confirm: {
      label: "확인",
      onClick: () => console.log("confirm"),
    },
    cancel: {
      label: "취소",
      onClick: () => console.log("cancel"),
    },
    children: <p>hello</p>,
  },
};
