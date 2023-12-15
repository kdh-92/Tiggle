import styled from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const MypageStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  .user-info {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 180px;
    padding: 60px 0;

    > .user {
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 8px;

      > .user-profile {
        border-radius: 50%;
        width: 64px;
        height: 64px;

        ${({ theme }) => theme.mq.desktop} {
          width: 80px;
          height: 80px;
        }
      }

      > .ant-avatar {
        width: 64px;
        height: 64px;
        background-color: ${({ theme }) =>
          theme.color.bluishGray[300].value} !important;

        ${({ theme }) => theme.mq.desktop} {
          width: 80px;
          height: 80px;
        }
      }
    }

    > .user-greeting {
      text-align: center;
      margin-bottom: 20px;
      ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
      color: ${({ theme: { color } }) => color.bluishGray[800].value};

      & span {
        color: ${({ theme: { color } }) => color.blue[600].value};
      }

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.title.large.bold)}
      }
    }

    > .profile-modify-button {
      width: fit-content;
      padding: 8px 24px;
      border-radius: 8px;
      background: ${({ theme }) => theme.color.bluishGray[100].value};
      color: ${({ theme: { color } }) => color.bluishGray[600].value};
      ${({ theme }) => expandTypography(theme.typography.body.small.bold)}

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
      }
    }
  }

  .user-transaction {
    margin-bottom: 80px;

    .transaction-title {
      color: ${({ theme: { color } }) => color.bluishGray[600].value};
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
      margin-bottom: 12px;

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
      }
    }

    .transaction-cells {
      width: 327px;
      background: ${({ theme }) => theme.color.white.value};
      border-radius: 16px;

      .transaction-cell {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 72px;
        padding: 24px 28px;
        color: ${({ theme: { color } }) => color.bluishGray[700].value};
        border-bottom: 1px solid
          ${({ theme: { color } }) => color.bluishGray[100].value};
        &:last-child {
          justify-content: center;
          align-items: center;
          border-bottom: none;
          padding: 0;
          > button {
            width: 100%;
            height: 100%;
            color: ${({ theme: { color } }) => color.blue[600].value};
            ${({ theme }) =>
              expandTypography(theme.typography.body.medium.bold)}

            ${({ theme }) => theme.mq.desktop} {
              ${({ theme }) =>
                expandTypography(theme.typography.body.large.bold)}
            }
          }
        }
      }

      ${({ theme }) => theme.mq.desktop} {
        width: 480px;
      }
    }
  }

  .user-setting {
    margin-bottom: 80px;

    .setting-title {
      color: ${({ theme: { color } }) => color.bluishGray[600].value};
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
      margin-bottom: 12px;

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
      }
    }
    .setting-cells {
      width: 327px;
      background: ${({ theme }) => theme.color.white.value};
      border-radius: 16px;
      .setting-cell {
        display: flex;
        align-items: center;
        justify-content: space-between;
        width: 100%;
        height: 62px;
        padding: 20px;
        color: ${({ theme: { color } }) => color.bluishGray[700].value};
        ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
        border-bottom: 1px solid
          ${({ theme: { color } }) => color.bluishGray[100].value};
        &:last-child {
          border-bottom: none;
        }
        > svg {
          color: ${({ theme: { color } }) => color.bluishGray[400].value};
        }

        ${({ theme }) => theme.mq.desktop} {
          padding: 24px 28px;
          height: 72px;
          ${({ theme }) =>
            expandTypography(theme.typography.title.small2x.medium)}
        }
      }

      ${({ theme }) => theme.mq.desktop} {
        width: 480px;
      }
    }
  }

  .logout-button {
    width: 327px;
    padding: 20px;
    border-radius: 12px;
    margin-bottom: 82px;
    background: ${({ theme }) => theme.color.white.value};
    color: ${({ theme: { color } }) => color.blue[600].value};
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}

    ${({ theme }) => theme.mq.desktop} {
      width: 480px;
      padding: 24px 28px;
      margin-bottom: 128px;
      ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
    }
  }
`;
