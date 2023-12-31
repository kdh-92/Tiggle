import { Meta, StoryObj } from "@storybook/react";

import SocialLoginButton from "@/pages/LoginPage/SocialLoginButton/SocialLoginButton";

export default {
  title: "atoms/SocialLoginButton",
  component: SocialLoginButton,
} as Meta<typeof SocialLoginButton>;

type Story = StoryObj<typeof SocialLoginButton>;

export const Default: Story = {
  args: {
    social_logo: "kakao",
  },
};
