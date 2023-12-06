import styled, { css } from "styled-components";

import { MenuProps } from "@/components/atoms/Menu/Menu";

import { expandTypography } from "../util";

export const MenuButtonStyle = styled.button`
  width: 36px;
  height: 36px;
  background-color: ${({ theme: { color } }) => color.bluishGray[50].value};
  border: 1px solid ${({ theme: { color } }) => color.bluishGray[200].value};
  border-radius: 8px;

  display: flex;
  align-items: center;
  justify-content: center;
  transition:
    background-color 0.1s ease,
    color 0.1s ease;
  cursor: pointer;

  .menu-icon {
    width: 16px;
    height: 16px;
    color: ${({ theme: { color } }) => color.bluishGray[400].value};
  }

  &:hover,
  &:active {
    background-color: ${({ theme: { color } }) => color.bluishGray[100].value};
    .menu-icon {
      color: ${({ theme: { color } }) => color.bluishGray[500].value};
    }
  }
`;

export const MenuItemStyle = styled.button`
  width: 100%;
  padding: 8px 0;
  color: ${({ theme }) => theme.color.bluishGray[600].value};
  ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}

  &:hover, &:active {
    background-color: ${({ theme }) => theme.color.bluishGray[50].value};
    color: ${({ theme }) => theme.color.bluishGray[800].value};
  }

  ${({ theme }) => theme.mq.desktop} {
    padding: 12px 0;
    ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
  }
`;

export const MenuStyle = styled.div<{ $align: MenuProps["align"] }>`
  width: fit-content;
  position: relative;

  .menu-items {
    width: 120px;
    padding: 8px 0;
    background-color: ${({ theme }) => theme.color.white.value};
    border-radius: 8px;
    box-shadow:
      0px 10px 15px -3px rgba(0, 0, 0, 0.1),
      0px 4px 6px -2px rgba(69, 85, 115, 0.05);

    display: none;
    flex-direction: column;
    gap: 4px;

    position: absolute;
    top: calc(100% + 4px);

    &.open {
      display: flex;
    }

    ${({ $align }) => {
      switch ($align) {
        case "left":
          return css`
            left: 0;
          `;
        case "right":
          return css`
            right: 0;
          `;
        case "center":
          return css`
            left: 50%;
            transform: translateX(-50%);
          `;
      }
    }};
  }

  ${({ theme }) => theme.mq.desktop} {
    .menu-items {
      width: 150px;
      top: calc(100% + 8px);
    }
  }
`;
