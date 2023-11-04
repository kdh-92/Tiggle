import { Meta, StoryObj } from "@storybook/react";
import styled from "styled-components";

import { Menu, MenuItem } from "@/components/atoms/Menu/Menu";

export default {
  title: "atoms/Menu",
  component: Menu,
  decorators: [
    Story => (
      <FlexWrapper>
        <Story />
      </FlexWrapper>
    ),
  ],
} as Meta<typeof Menu>;

type Story = StoryObj<typeof Menu>;

export const Default: Story = {
  args: {
    children: (
      <>
        <MenuItem label="수정하기" />
        <MenuItem label="삭제하기" />
      </>
    ),
  },
};

const FlexWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
