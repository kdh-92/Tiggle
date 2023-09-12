import { Meta, StoryObj } from "@storybook/react";

import PostHeader from "@/components/molecules/PostHeader/PostHeader";
import { Tx } from "@/types";

export default {
  title: "molecules/PostHeader",
  component: PostHeader,
} as Meta<typeof PostHeader>;

type Story = StoryObj<typeof PostHeader>;

export const Default: Story = {
  args: {
    id: 0,
    content: "제목 텍스트",
    amount: 50000,
    txType: Tx.OUTCOME,
    user: {
      name: "사용자 이름",
      profileUrl: "image.jpg",
    },
    date: "2023-08-06T06:00:00.000Z",
    category: "카테고리 텍스트",
    asset: "자산 텍스트",
  },
};
