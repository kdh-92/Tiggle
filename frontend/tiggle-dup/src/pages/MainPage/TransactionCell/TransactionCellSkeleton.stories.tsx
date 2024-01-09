import { Meta, StoryObj } from "@storybook/react";

import TransactionCellSkeleton from "@/pages/MainPage/TransactionCell/TransactionCellSkeleton";

export default {
  title: "molecules/TransactionCellSkeleton",
  component: TransactionCellSkeleton,
} as Meta<typeof TransactionCellSkeleton>;

type Story = StoryObj<typeof TransactionCellSkeleton>;

export const Default: Story = {
  args: {},
};
