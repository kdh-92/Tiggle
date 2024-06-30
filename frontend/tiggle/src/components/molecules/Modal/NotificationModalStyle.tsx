import { styled } from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const NotificationModalStyle = styled.div`
  position: fixed;
  overflow: hidden;
  width: 100%;
  z-index: 9999;

  ${({ theme }) => theme.mq.desktop} {
    position: fixed;
    overflow: auto;
  }

  .notification-background {
    width: auto;
    height: 100vh;
    display: flex;
    padding: 0;
    justify-content: center;

    ${({ theme }) => theme.mq.desktop} {
      padding: 0 36px 0 0;
    }
  }

  /* wrap */
  .notification-modal-wrap {
    width: 100%;
    height: fit-content;
    display: flex;
    justify-content: flex-end;

    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
    }
  }

  .notification-modal {
    box-sizing: border-box;

    /* Auto layout */
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 24px;
    gap: 24px;

    width: 100vw;
    height: 100vh;
    left: 324px;
    top: 64px;

    background: ${({ theme: { color } }) => color.bluishGray[50].value};

    ${({ theme }) => theme.mq.desktop} {
      width: 408px;
      height: 184px;
      max-height: 600px;
      border: 1px solid ${({ theme: { color } }) => color.bluishGray[200].value};
      box-shadow:
        0px 4px 6px -2px rgba(69, 85, 115, 0.05),
        0px 10px 15px -3px rgba(0, 0, 0, 0.1);
      border-radius: 16px;
    }
  }

  .notification-title {
    line-height: 32px;
    ${({ theme }) => expandTypography(theme.typography.title.medium.bold)};
    color: ${({ theme: { color } }) => color.bluishGray[800].value};

    ${({ theme }) => theme.mq.desktop} {
      line-height: 24px;
      ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)};
    }
  }

  .notification-content-wrap {
    width: 100%;
    height: 100%;
    flex-direction: column;
    justify-content: center;
    display: flex;
  }

  .notification-content-box {
    display: flex;
    justify-content: center;
  }

  .notification-content {
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)};
    color: ${({ theme: { color } }) => color.bluishGray[400].value};

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 20px 0px;
    gap: 4px;

    width: 360px;
    height: 88px;

    flex: none;
    order: 1;
    align-self: stretch;
    flex-grow: 0;

    > p {
      color: ${({ theme: { color } }) => color.bluishGray[600].value};
    }
  }
`;
