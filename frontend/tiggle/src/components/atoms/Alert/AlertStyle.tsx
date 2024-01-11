import styled, { keyframes } from "styled-components";

const overlayFadeIn = keyframes`
    0% {
        opacity: 0;
    }
    100% {
        opacity: 1;
    }
`;

const overlayFadeOut = keyframes`
    0% {
        opacity: 1;
    }
    100% {
        opacity: 0;
    }
`;

export const OverlayContainerStyle = styled.div`
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 999;

  .overlay {
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: rgba(0, 0, 0, 0.32);

    &.open {
      animation: ${overlayFadeIn} 0.2s ease;
    }

    &.close {
      animation: ${overlayFadeOut} 0.2s ease forwards;
    }
  }
`;

const alertSlideUp = keyframes`
    0% {
        transform: translateY(100%);
    } 
    100% {
        transform: translateY(0%);
    }
`;

const alertSlideDown = keyframes`
    0% {
        transform: translateY(0%);
    }
    50% {
        opacity: 1;
    }
    100% {
        transform: translateY(100%);
        opacity: 0;
    }
`;

const alertFadeIn = keyframes`
    0% {
        opacity: 0;
    }
    100% {
        opacity: 1;
    }
`;

const alertFadeOut = keyframes`
    0% {
        opacity: 1;
    }
    100% {
        opacity: 0;
    }
`;

export const AlertStyle = styled.div`
  padding: 32px 24px 20px;
  min-height: 180px;
  max-height: 560px;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 12px;

  display: flex;
  flex-direction: column;

  position: absolute;
  left: 24px;
  right: 24px;
  bottom: 60px;

  &.open {
    animation: ${alertSlideUp} 0.3s cubic-bezier(0.29, 1.05, 0.55, 1);
  }
  &.close {
    animation: ${alertSlideDown} 0.2s ease forwards;
  }

  .alert-content {
    flex-grow: 1;
    overflow-y: scroll;
  }

  .alert-footer {
    width: 100%;
    display: flex;
    gap: 8px;

    flex-shrink: 0;
  }

  ${({ theme }) => theme.mq.desktop} {
    width: 380px;
    padding: 48px 32px 24px;
    border-radius: 16px;

    top: 200px;
    left: 50%;
    right: unset;
    bottom: unset;
    transform: translateX(-50%);

    &.open {
      animation: ${alertFadeIn} 0.2s ease;
    }
    &.close {
      animation: ${alertFadeOut} 0.1s ease forwards;
    }
  }
`;
