import { Meta, StoryObj } from "@storybook/react";

import BottomSheet from "./BottomSheet";

export default {
  title: "atoms/BottomSheet",
  component: BottomSheet,
} as Meta<typeof BottomSheet>;

type Story = StoryObj<typeof BottomSheet>;

export const Default: Story = {
  args: {
    isOpen: true,
    header: {
      title: "선택하기",
    },
    confirm: {
      label: "확인",
      onClick: () => console.log("확인"),
    },
    onClose: () => console.log("close"),
    children: <p>hello</p>,
  },
};
