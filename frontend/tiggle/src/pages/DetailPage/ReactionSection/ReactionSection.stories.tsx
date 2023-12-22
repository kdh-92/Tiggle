import { Meta, StoryObj } from "@storybook/react";

import ReactionSection from "@/pages/DetailPage/ReactionSection/ReactionSection";

export default {
  title: "molecules/ReactionSection",
  component: ReactionSection,
} as Meta<typeof ReactionSection>;

type Story = StoryObj<typeof ReactionSection>;

export const Default: Story = {
  args: {
    txId: 1,
    upCount: 100,
    downCount: 200,
  },
};
