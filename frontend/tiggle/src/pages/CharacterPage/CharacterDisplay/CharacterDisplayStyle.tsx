import styled, { css, keyframes } from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

const pulseGlow = keyframes`
  0%, 100% {
    filter: drop-shadow(0 0 12px var(--char-color)) drop-shadow(0 0 24px var(--char-color));
  }
  50% {
    filter: drop-shadow(0 0 20px var(--char-color)) drop-shadow(0 0 40px var(--char-color));
  }
`;

const holoGradient = keyframes`
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
`;

export const CharacterDisplayWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
`;

export const CharacterOrb = styled.div<{
  $hue: number;
  $saturation: number;
  $lightness: number;
  $rarity: string;
  $isEgg: boolean;
}>`
  --char-color: hsl(
    ${({ $hue }) => $hue},
    ${({ $saturation }) => $saturation}%,
    ${({ $lightness }) => $lightness}%
  );

  width: 160px;
  height: 160px;
  border-radius: ${({ $isEgg }) =>
    $isEgg ? "50% 50% 50% 50% / 40% 40% 60% 60%" : "50%"};
  background: var(--char-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  transition: all 0.3s ease;

  ${({ theme }) => theme.mq.desktop} {
    width: 200px;
    height: 200px;
  }

  ${({ $rarity, $hue }) =>
    $rarity === "SHINE" &&
    css`
      box-shadow:
        0 0 20px hsla(${$hue}, 70%, 60%, 0.5),
        0 0 40px hsla(${$hue}, 70%, 60%, 0.2);
    `}

  ${({ $rarity, $hue }) =>
    $rarity === "HOLOGRAPHIC" &&
    css`
      background: linear-gradient(
        135deg,
        hsl(${$hue}, 70%, 60%),
        hsl(${$hue + 15}, 70%, 55%),
        hsl(${$hue + 30}, 70%, 60%)
      );
      background-size: 200% 200%;
      animation: ${holoGradient} 3s ease infinite;
    `}

  ${({ $rarity, $hue }) =>
    $rarity === "LEGENDARY" &&
    css`
      background: linear-gradient(
        135deg,
        hsl(${$hue}, 80%, 55%),
        hsl(${$hue + 15}, 80%, 50%),
        hsl(${$hue + 30}, 80%, 55%)
      );
      background-size: 200% 200%;
      animation:
        ${holoGradient} 3s ease infinite,
        ${pulseGlow} 2s ease-in-out infinite;
    `}

  @media (prefers-reduced-motion: reduce) {
    animation: none !important;
    transition: none;
  }
`;

export const EggPhaseText = styled.span`
  color: rgba(255, 255, 255, 0.9);
  ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
  text-align: center;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
`;

export const EggProgressText = styled.span`
  color: rgba(255, 255, 255, 0.7);
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.15);
`;

export const FormName = styled.span`
  color: rgba(255, 255, 255, 0.95);
  ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)}
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
  text-align: center;
`;

export const TierBadge = styled.span<{ $tierColor: string }>`
  display: inline-block;
  padding: 2px 10px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.25);
  color: ${({ $tierColor }) => $tierColor};
  ${({ theme }) => expandTypography(theme.typography.body.small2x.bold)}
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.15);
  backdrop-filter: blur(4px);
`;

export const RarityBadge = styled.div<{ $rarity: string }>`
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 12px;
  border-radius: 16px;
  ${({ theme }) => expandTypography(theme.typography.body.small.bold)}

  ${({ $rarity, theme }) => {
    switch ($rarity) {
      case "SHINE":
        return css`
          background: ${theme.color.blue[50].value};
          color: ${theme.color.blue[600].value};
        `;
      case "HOLOGRAPHIC":
        return css`
          background: linear-gradient(135deg, #e0e7ff, #fce7f3);
          color: #7c3aed;
        `;
      case "LEGENDARY":
        return css`
          background: linear-gradient(135deg, #fef3c7, #fde68a);
          color: #b45309;
        `;
      default:
        return css`
          background: ${theme.color.bluishGray[100].value};
          color: ${theme.color.bluishGray[600].value};
        `;
    }
  }}
`;

export const CharacterInfoRow = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
  justify-content: center;
`;
