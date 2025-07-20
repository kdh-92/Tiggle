import { Meta, StoryObj } from "@storybook/react";

import PostHeader, {
  PostHeaderProps,
} from "@/pages/DetailPage/PostHeader/PostHeader";
import { Tx, TxType } from "@/types";

type PostHeaderPropsWithTxType = PostHeaderProps & { txType?: TxType };

export default {
  title: "molecules/PostHeader",
  component: PostHeader,
  render: ({ txType, ...args }) => {
    return <PostHeader {...args} />;
  },
  argTypes: {
    txType: {
      options: Object.values(Tx),
      control: { type: "radio" },
    },
  },
} as Meta<PostHeaderPropsWithTxType>;

type Story = StoryObj<PostHeaderPropsWithTxType>;

export const Default: Story = {
  args: {
    id: 0,
    content: "제목 텍스트",
    amount: 50000,
    sender: {
      id: 1,
      email: "test@example.com",
      nickname: "사용자이름",
      profileUrl: "",
    },
    date: "2023-08-06T06:00:00.000Z",
    txType: "OUTCOME",
  },
};
