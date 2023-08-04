import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ReactionButtonStyle = styled.button`
  width: 128px;
  padding: 12px 0;
  background-color: ${({ theme: { color } }) => color.white.value};
  color: ${({ theme: { color } }) => color.bluishGray[500].value};
  border: 1px solid ${({ theme: { color } }) => color.bluishGray[200].value};
  border-radius: 8px;

  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;

  transition:
    color 0.1s ease,
    background-color 0.1s ease;
  cursor: pointer;

  .label {
    display: flex;
    align-items: center;
    gap: 4px;
    &-text {
      ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    }
    &-icon {
      width: 20px;
      height: 20px;
    }
  }

  .number {
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
  }

  &:hover {
    background-color: ${({ theme: { color } }) => color.bluishGray[50].value};
  }
  &:active {
    background-color: ${({ theme: { color } }) => color.bluishGray[100].value};
    color: ${({ theme: { color } }) => color.bluishGray[600].value};
  }

  &.checked {
    &.outcome {
      background-color: ${({ theme: { color } }) => color.peach[50].value};
      color: ${({ theme: { color } }) => color.peach[600].value};
      border-color: ${({ theme: { color } }) => color.peach[300].value};
      &:hover {
        background-color: ${({ theme: { color } }) => color.peach[100].value};
      }
      &:active {
        background-color: ${({ theme: { color } }) => color.peach[200].value};
      }
    }
    &.refund {
      background-color: ${({ theme: { color } }) => color.blue[50].value};
      color: ${({ theme: { color } }) => color.blue[600].value};
      border-color: ${({ theme: { color } }) => color.blue[300].value};
      &:hover {
        background-color: ${({ theme: { color } }) => color.blue[100].value};
      }
      &:active {
        background-color: ${({ theme: { color } }) => color.blue[200].value};
      }
    }
  }

  ${({ theme }) => theme.mq.desktop} {
    width: 192px;
    padding: 16px 0;

    .label-text {
      ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
    }
    .label-icon {
      width: 22px;
      height: 22px;
    }
    .number {
      ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)}
    }
  }
`;
