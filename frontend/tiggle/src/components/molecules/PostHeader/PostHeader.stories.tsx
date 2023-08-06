import { Meta, StoryObj } from "@storybook/react";

import PostHeader from "@/components/molecules/PostHeader/PostHeader";

export default {
  title: "molecules/PostHeader",
  component: PostHeader,
} as Meta<typeof PostHeader>;

type Story = StoryObj<typeof PostHeader>;

export const Default: Story = {
  args: {
    id: 0,
    title: "제목 텍스트",
    amount: 50000,
    txType: "outcome",
    user: {
      name: "사용자 이름",
      profileUrl: "image.jpg",
    },
    date: "2024.01.01",
    category: "카테고리 텍스트",
    asset: "자산 텍스트",
  },
};
