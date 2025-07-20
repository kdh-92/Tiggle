import { Meta, StoryObj } from "@storybook/react";

import TransactionCell from "@/pages/MainPage/TransactionCell/TransactionCell";
import { Tx } from "@/types";

export default {
  title: "molecules/TransactionCell",
  component: TransactionCell,
} as Meta<typeof TransactionCell>;

type Story = StoryObj<typeof TransactionCell>;

export const Default: Story = {
  args: {
    dto: {
      id: 1,
      amount: 50000,
      content: "컨텐츠 제목",
      reason: "컨텐츠 설명 컨텐츠 설명 컨텐츠 설명",
      date: "2023-08-15",
      category: {
        id: 1,
        name: "식비",
        defaults: true,
      },
      member: {
        id: 1,
        email: "user@example.com",
        nickname: "사용자 이름",
        profileUrl: "",
      },
      createdAt: "2023-08-15T08:47:19",
    },
    upCount: 0,
    downCount: 0,
    commentCount: 0,
  },
};
