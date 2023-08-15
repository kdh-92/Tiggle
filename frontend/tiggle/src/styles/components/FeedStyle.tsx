import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const FeedStyle = styled.div`
  margin-bottom: 24px;
  border-radius: 24px;
  border: 1px solid ${({ theme: { color } }) => color.gray[200].value};
  background-color: ${({ theme: { color } }) => color.gray[50].value};
  padding: 24px;
  color: ${({ theme }) => theme.color.gray[900].value};

  &.outcome:hover {
    background-color: ${({ theme }) => theme.color.peach[50].value};
    border: 1px solid ${({ theme: { color } }) => color.peach[200].value};
  }

  &.refund:hover {
    background-color: ${({ theme }) => theme.color.blue[50].value};
    border: 1px solid ${({ theme: { color } }) => color.blue[200].value};
  }

  .tag {
    margin-bottom: 8px;
  }

  .amount {
    display: flex;
    align-items: center;
    gap: 8px;

    &-unit {
      ${({ theme }) => expandTypography(theme.typography.title.medium.bold)};

      ${({ theme }) => theme.mq.mobile} {
        ${({ theme }) => expandTypography(theme.typography.title.small.bold)};
      }
    }

    &.outcome {
      color: ${({ theme }) => theme.color.peach[600].value};
    }

    &.refund {
      color: ${({ theme }) => theme.color.blue[600].value};
    }
  }

  .feed-section {
    padding: 32px 0;

    > .title {
      ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)};

      ${({ theme }) => theme.mq.mobile} {
        ${({ theme }) => expandTypography(theme.typography.body.large.bold)};
      }
    }

    > .content {
      opacity: 0.6;
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)};

      ${({ theme }) => theme.mq.mobile} {
        ${({ theme }) => expandTypography(theme.typography.body.small.regular)};
      }
    }
  }

  .feed-footer {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    color: ${({ theme }) => theme.color.gray[700].value};
  }

  .user {
    display: flex;
    align-items: center;
    gap: 8px;

    &-profile {
      width: 34px;
      height: 34px;
      border-radius: 50%;
      background-color: ${({ theme }) => theme.color.gray[500].value};

      ${({ theme }) => theme.mq.mobile} {
        width: 30px;
        height: 30px;
      }
    }

    &-name {
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)};

      ${({ theme }) => theme.mq.mobile} {
        ${({ theme }) => expandTypography(theme.typography.body.small.bold)};
      }
    }

    &-createdAt {
      opacity: 0.6;
      ${({ theme }) => expandTypography(theme.typography.body.small.regular)};

      ${({ theme }) => theme.mq.mobile} {
        ${({ theme }) =>
          expandTypography(theme.typography.body.small2x.regular)};
      }
    }
  }

  .ant-avatar {
    background-color: ${({ theme }) => theme.color.gray[500].value} !important;
  }

  .icon-unit {
    opacity: 0.5;
    display: flex;
    ${({ theme }) => expandTypography(theme.typography.body.small.medium)};

    ${({ theme }) => theme.mq.mobile} {
      ${({ theme }) => expandTypography(theme.typography.body.small2x.medium)};
    }

    //TODO: 더 간략하게 css를 줄 수 있을 것 같다
    > .reaction,
    .comment {
      display: flex;
      align-items: center;
    }

    > .reaction > div {
      display: flex;
      align-items: center;
      gap: 2px;
    }

    .reaction-smile {
      margin-right: 8px;
    }

    .reaction-frown {
      margin-right: 16px;
    }

    .comment {
      gap: 2px;
    }

    .label-icon {
      width: 12px;

      ${({ theme }) => theme.mq.desktop} {
        width: 14px;
      }
    }
  }
`;
