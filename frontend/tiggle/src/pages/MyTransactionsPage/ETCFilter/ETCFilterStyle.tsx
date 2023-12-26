import styled, { keyframes } from "styled-components";

import { expandTypography } from "@/styles/util";

export const ETCFilterStyle = styled.div`
  padding: 16px;

  ${({ theme }) => theme.mq.desktop} {
    padding: 20px;
  }
`;

export const ETCFilterHeaderStyle = styled.div`
  display: flex;
  flex-direction: column;
  gap: 12px;

  .title {
    display: flex;
    justify-content: space-between;
    align-items: center;

    &-text {
      display: flex;
      align-items: center;
      gap: 8px;
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}

      > svg {
        width: 16px;
        height: 16px;
        color: ${({ theme }) => theme.color.bluishGray[500].value};
      }
    }

    &-toggle {
      width: 20px;
      height: 20px;
      color: ${({ theme }) => theme.color.bluishGray[600].value};
      > svg {
        width: 100%;
        height: 100%;
        display: none;
        &.show {
          display: block;
        }
      }
    }
  }

  .selected-filter {
    display: flex;
    flex-flow: row wrap;
    gap: 6px;
  }

  ${({ theme }) => theme.mq.desktop} {
    .title {
      &-text {
        ${({ theme }) => expandTypography(theme.typography.body.large.bold)}

        > svg {
          width: 20px;
          height: 20px;
        }
      }

      &-toggle {
        width: 24px;
        height: 24px;
      }
    }
  }
`;

const accordionOpen = keyframes`
  0% {
    overflow: hidden;
  }
  99% {
    overflow: hidden;
  }
  100% {
    overflow: visible;
  }
`;

export const ETCFilterAccordionStyle = styled.div<{ _height: number }>`
  max-height: ${({ _height }) => _height}px;
  transition: max-height 0.3s ease;

  .container {
    padding-top: 24px;
    display: flex;
    flex-direction: row;
    gap: 8px;
  }

  &.open {
    animation: ${accordionOpen} 0.3s ease forwards;
  }

  &.close {
    overflow: hidden;
  }
`;
