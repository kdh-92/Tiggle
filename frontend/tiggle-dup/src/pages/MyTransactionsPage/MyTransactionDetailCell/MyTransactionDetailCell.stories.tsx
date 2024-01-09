import { Meta, StoryObj } from "@storybook/react";

import MyTransactionDetailCell from "./MyTransactionDetailCell";

export default {
  title: "MyTransactionsPage/MyTransactionDetailCell",
  component: MyTransactionDetailCell,
} as Meta<typeof MyTransactionDetailCell>;

type Story = StoryObj<typeof MyTransactionDetailCell>;

export const Detault: Story = {
  args: {
    id: 1,
    amount: 50000,
    type: "INCOME",
    content: "제목 텍스트 텍스트",
    reason: "설명 텍스트 텍스트 설명 텍스트 텍스트 설명 텍스트 텍스트",
  },
};
