import { useEffect } from "react";
import { useDispatch } from "react-redux";

import { Meta, StoryObj } from "@storybook/react";

import CommentCell, {
  CommentCellProps,
} from "@/components/molecules/CommentCell/CommentCell";
import detailPageStore from "@/store/detailPage";
import { Tx, TxType } from "@/types";

type CommentCellPropsWithTxType = CommentCellProps & { txType: TxType };

export default {
  title: "molecules/CommentCell",
  component: CommentCell,
  render: ({ txType, ...args }) => {
    const dispatch = useDispatch();
    useEffect(() => {
      dispatch(detailPageStore.actions.creators.setType(txType));
    }, [txType]);
    return <CommentCell {...args} />;
  },
  argTypes: {
    txType: {
      options: Object.values(Tx),
      control: { type: "radio" },
    },
  },
} as Meta<CommentCellPropsWithTxType>;

type Story = StoryObj<CommentCellPropsWithTxType>;

export const Default: Story = {
  args: {
    id: 0,
    txId: 3,
    sender: {
      nickname: "사용자이름",
    },
    createdAt: "2023-08-06T06:00:00.000Z",
    content:
      "모든 국민은 건강하고 쾌적한 환경에서 생활할 권리를 가지며, 국가와 국민은 환경보전을 위하여 노력하여야 한다.",
    childCount: 0,
    txType: Tx.REFUND,
  },
};
