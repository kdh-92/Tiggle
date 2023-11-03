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
    content: "컨텐츠 제목",
    reason: "컨텐츠 설명 컨텐츠 설명 컨텐츠 설명",
    member: {
      nickname: "사용자 이름",
      profileUrl: "",
    },
    createdAt: "2023-08-15T08:47:19",
    // number: 89,
  },
};
