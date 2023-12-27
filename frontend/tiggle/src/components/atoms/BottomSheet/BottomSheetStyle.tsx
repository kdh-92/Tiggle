import styled, { keyframes } from "styled-components";

import { expandTypography } from "@/styles/util";

const bottomSheetOverlayFadeIn = keyframes`
    0% {
        opacity: 0;
    }
    100% {
        opacity: 1;
    }
`;

const bottomSheetOverlayFadeOut = keyframes`
    0% {
        opacity: 1;
    }
    100% {
        opacity: 0;
    }
`;

export const BottomSheetContainerStyle = styled.div`
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 999;

  .bottom-sheet-overlay {
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: rgba(0, 0, 0, 0.32);

    &.open {
      animation: ${bottomSheetOverlayFadeIn} 0.3s ease;
    }

    &.close {
      animation: ${bottomSheetOverlayFadeOut} 0.3s ease forwards;
    }
  }

  .bottom-sheet-content {
    flex-grow: 1;
    overflow-y: scroll;
  }
`;

const bottomSheetSlideUp = keyframes`
    0% {
        transform: translateY(100%);
    }
    100% {
        transform: translateY(0);
    }
`;

const bottomSheetSlideDown = keyframes`
    0% {
        transform: translateY(0%);
    }
    100% {
        transform: translateY(100%);
    }
`;

export const BottomSheetStyle = styled.div`
  width: 100%;
  height: 440px; // TODO: mobile 맞춰 사이즈 변경
  max-height: 100%;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 12px 12px 0 0;

  display: flex;
  flex-direction: column;

  position: absolute;
  bottom: 0;

  &.open {
    animation: ${bottomSheetSlideUp} 0.5s cubic-bezier(0.29, 1.05, 0.55, 1);
  }

  &.close {
    animation: ${bottomSheetSlideDown} 0.3s ease forwards;
  }
`;

export const BottomSheetHeaderStyle = styled.div`
  padding: 24px;
  display: flex;
  justify-content: space-between;

  .title {
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
    color: ${({ theme }) => theme.color.bluishGray[800].value};
  }

  .reset {
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    color: ${({ theme }) => theme.color.blue[500].value};
  }
`;

export const BottomSheetFooterStyle = styled.div`
  padding: 8px 24px 24px;
`;
