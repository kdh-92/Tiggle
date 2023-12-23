import styled, { keyframes } from "styled-components";

import { expandTypography } from "@/styles/util";

const popoverUnfold = keyframes`
  0% {
    opacity: 0;
    transform: scale(75%);
  }
  100% {
    opacity: 1;
    transform: scale(100%);
  }
`;

const popoverFold = keyframes`
  0% {
    opacity: 1;
    transform: scale(100%)
  }
  100% {
    opacity: 0;
    transform: scale(75%)
  }
`;

export const PopoverStyle = styled.div`
  width: 280px;
  height: 300px;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 16px;
  box-shadow:
    0px 10px 15px -3px rgba(0, 0, 0, 0.1),
    0px 4px 6px -2px rgba(69, 85, 115, 0.05);
  overflow-y: hidden;

  display: flex;
  flex-direction: column;

  position: absolute;
  top: calc(100% + 8px);

  .popover-content {
    overflow-y: scroll;
    flex-shrink: 1;
  }

  transform-origin: top center;
  &.open {
    animation: ${popoverUnfold} 0.3s cubic-bezier(0.29, 1.05, 0.55, 1);
  }

  &.close {
    animation: ${popoverFold} 0.15s ease forwards;
  }
`;

export const PopoverHeaderStyle = styled.div`
  padding: 16px;
  display: flex;
  justify-content: space-between;

  .title {
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
  }

  .reset {
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    color: ${({ theme }) => theme.color.blue[500].value};
  }
`;
