import { Meta, StoryObj } from "@storybook/react";

import Banner from "@/pages/MainPage/Banner/Banner";

export default {
  title: "molecules/Banner",
  component: Banner,
} as Meta<typeof Banner>;

type Story = StoryObj<typeof Banner>;

export const Default: Story = {
  args: {},
};
