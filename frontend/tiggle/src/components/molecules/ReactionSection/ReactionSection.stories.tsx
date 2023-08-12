import { Meta, StoryObj } from "@storybook/react";

import ReactionSection from "@/components/molecules/ReactionSection/ReactionSection";

export default {
  title: "molecules/ReactionSection",
  component: ReactionSection,
} as Meta<typeof ReactionSection>;

type Story = StoryObj<typeof ReactionSection>;

export const Default: Story = {
  args: {
    txType: "outcome",
    reactions: {
      Up: 100,
      Down: 200,
    },
    onAddReaction: reaction => {
      console.log("reaction added!", reaction);
    },
    onCancelReaction: () => {
      console.log("reaction canceled!");
    },
  },
};
