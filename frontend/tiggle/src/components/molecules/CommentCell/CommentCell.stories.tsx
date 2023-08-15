import { Meta, StoryObj } from "@storybook/react";

import CommentCell from "@/components/molecules/CommentCell/CommentCell";

export default {
  title: "molecules/CommentCell",
  component: CommentCell,
} as Meta<typeof CommentCell>;

type Story = StoryObj<typeof CommentCell>;

export const Default: Story = {
  args: {
    txType: "OUTCOME",
    comment: {
      id: 0,
      user: {
        name: "사용자이름",
        profileUrl: "profile.jpg",
      },
      createdAt: "2023-08-06T06:00:00.000Z",
      content:
        "모든 국민은 건강하고 쾌적한 환경에서 생활할 권리를 가지며, 국가와 국민은 환경보전을 위하여 노력하여야 한다.",
      replies: [
        {
          id: 1,
          user: {
            name: "사용자이름2",
            profileUrl: "profile.jpg",
          },
          createdAt: "2023-08-06T06:00:00.000Z",
          content:
            "모든 국민은 건강하고 쾌적한 환경에서 생활할 권리를 가지며, 국가와 국민은 환경보전을 위하여 노력하여야 한다.",
        },
        {
          id: 2,
          user: {
            name: "사용자이름3",
            profileUrl: "profile.jpg",
          },
          createdAt: "2023-08-06T06:00:00.000Z",
          content:
            "모든 국민은 건강하고 쾌적한 환경에서 생활할 권리를 가지며, 국가와 국민은 환경보전을 위하여 노력하여야 한다.",
        },
      ],
    },
  },
};
