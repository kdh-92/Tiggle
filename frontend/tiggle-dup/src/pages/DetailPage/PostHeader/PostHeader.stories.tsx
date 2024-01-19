import { useEffect } from "react";
import { useDispatch } from "react-redux";

import { Meta, StoryObj } from "@storybook/react";

import PostHeader, {
  PostHeaderProps,
} from "@/pages/DetailPage/PostHeader/PostHeader";
import detailPageStore from "@/store/detailPage";
import { Tx, TxType } from "@/types";

type PostHeaderPropsWithTxType = PostHeaderProps & { txType?: TxType };

export default {
  title: "molecules/PostHeader",
  component: PostHeader,
  render: ({ txType, ...args }) => {
    const dispatch = useDispatch();
    useEffect(() => {
      dispatch(detailPageStore.actions.setType(txType!));
    }, [txType]);
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
      nickname: "사용자 이름",
      profileUrl: "image.jpg",
    },
    date: "2023-08-06T06:00:00.000Z",
    category: "카테고리 텍스트",
    asset: "자산 텍스트",
    txType: Tx.OUTCOME,
  },
};
