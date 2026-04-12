import styled, { css, keyframes } from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

const rainbowBorder = keyframes`
  0% { border-color: #ff0000; }
  17% { border-color: #ff8800; }
  33% { border-color: #ffff00; }
  50% { border-color: #00ff00; }
  67% { border-color: #0088ff; }
  83% { border-color: #8800ff; }
  100% { border-color: #ff0000; }
`;

export const tierBorderColor: Record<string, string> = {
  COMMON: "#999999",
  RARE: "#438bea",
  EPIC: "#9333ea",
  LEGENDARY: "#eab308",
  UNIQUE: "#ff0000",
};

export const ItemCardContainer = styled.div<{
  $tier: string;
  $locked?: boolean;
}>`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 12px;
  border-radius: 12px;
  border: 2px solid ${({ $tier }) => tierBorderColor[$tier] ?? "#999999"};
  background: ${({ theme }) => theme.color.white.value};
  cursor: pointer;
  transition:
    transform 0.15s ease,
    box-shadow 0.15s ease;
  position: relative;
  overflow: hidden;

  ${({ $tier }) =>
    $tier === "UNIQUE" &&
    css`
      animation: ${rainbowBorder} 3s linear infinite;
    `}

  ${({ $locked }) =>
    $locked &&
    css`
      opacity: 0.45;
      filter: grayscale(100%);
      cursor: default;
    `}

  &:hover {
    ${({ $locked }) =>
      !$locked &&
      css`
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      `}
  }
`;

export const ItemImage = styled.div`
  width: 56px;
  height: 56px;
  border-radius: 8px;
  background: ${({ theme }) => theme.color.bluishGray[100].value};
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 8px;
  font-size: 24px;

  ${({ theme }) => theme.mq.desktop} {
    width: 72px;
    height: 72px;
  }
`;

export const ItemName = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
  color: ${({ theme }) => theme.color.bluishGray[800].value};
  text-align: center;
  word-break: keep-all;
`;

export const ItemSlotLabel = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  color: ${({ theme }) => theme.color.bluishGray[400].value};
  margin-top: 2px;
`;

export const LockOverlay = styled.div`
  position: absolute;
  top: 6px;
  right: 6px;
  font-size: 14px;
`;

export const TierBadge = styled.span<{ $tier: string }>`
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  color: ${({ $tier }) => tierBorderColor[$tier] ?? "#999999"};
  margin-top: 4px;
`;
