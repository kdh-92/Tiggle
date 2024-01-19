import { Meta, StoryObj } from "@storybook/react";

import Upload from "./Upload";

export default {
  title: "atoms/Upload",
  component: Upload,
} as Meta<typeof Upload>;

type Story = StoryObj<typeof Upload>;

export const Default: Story = {
  args: {
    onChange: e => console.log(e.target.files),
    onReset: () => console.log("remove"),
  },
};
