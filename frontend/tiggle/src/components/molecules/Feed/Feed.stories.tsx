import { Meta, StoryObj } from "@storybook/react";

import Feed from "@/components/molecules/Feed/Feed";
import { Tx } from "@/types";

export default {
  title: "molecules/Feed",
  component: Feed,
} as Meta<typeof Feed>;

type Story = StoryObj<typeof Feed>;

export const Default: Story = {
  args: {
    id: 1,
    txType: Tx.Outcome,
    amount: 50000,
    title: "거래 제목",
    content: "거래 설명",
    user: {
      name: "사용자 이름",
      profileUrl: "image.jpg",
    },
    createdAt: "4시간 전",
    number: 89,
  },
};
