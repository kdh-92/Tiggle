import { useState } from "react";

import { Meta, StoryObj } from "@storybook/react";
import styled from "styled-components";

import { CTAButton } from "@/components/atoms";

import PopOver from "./PopOver";

export default {
  title: "atoms/PopOver",
  component: PopOver,
} as Meta<typeof PopOver>;

type Story = StoryObj<typeof PopOver>;

const PopOverStorybookChildrenStyle = styled.ul`
  li {
    padding: 12px 16px;
    &:not(:last-child) {
      border-bottom: 1px solid #dfe4ec;
    }
  }
`;

const PopOverStorybookContainerStyle = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  position: relative;
`;

export const Default: Story = {
  args: {
    header: {
      title: "선택하기",
      reset: true,
    },
    children: (
      <PopOverStorybookChildrenStyle>
        <li>option 1</li>
        <li>option 2</li>
        <li>option 3</li>
        <li>option 4</li>
        <li>option 5</li>
        <li>option 6</li>
      </PopOverStorybookChildrenStyle>
    ),
  },
  render: args => {
    const [isOpen, setIsOpen] = useState(false);

    const toggle = () => setIsOpen(!isOpen);

    return (
      <PopOverStorybookContainerStyle>
        <CTAButton onClick={toggle}>toggle</CTAButton>
        <PopOver {...args} isOpen={isOpen} />
      </PopOverStorybookContainerStyle>
    );
  },
};
