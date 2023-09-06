import { Meta, StoryObj } from "@storybook/react";

import TransactionCell from "@/components/molecules/TransactionCell/TransactionCell";
import { Tx } from "@/types";

export default {
  title: "molecules/TransactionCell",
  component: TransactionCell,
} as Meta<typeof TransactionCell>;

type Story = StoryObj<typeof TransactionCell>;

export const Default: Story = {
  args: {
    id: 1,
    type: Tx.OUTCOME,
    amount: 50000,
    content: "거래 제목",
    reason: "거래 설명",
    user: {
      name: "사용자 이름",
      imageUrl: "image.jpg",
    },
    createdAt: "2023-08-15T08:47:19",
    number: 89,
  },
};
