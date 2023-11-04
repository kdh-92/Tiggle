import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const TransactionCellStyle = styled.div`
  margin-bottom: 24px;
  border-radius: 24px;
  background-color: ${({ theme: { color } }) => color.white.value};
  padding: 24px;
  color: ${({ theme }) => theme.color.gray[900].value};

  /*TODO: 자식 div 자체에 width를 주면 padding 값까지 포함되는 문제. 
  부모에게서 width를 받아오면 스토리북에는 적용 안 되는 문제. */
  ${({ theme }) => theme.mq.desktop} {
    width: calc(340px - 48px);
  }

  &.OUTCOME:hover {
    background-color: ${({ theme }) => theme.color.peach[50].value};
  }

  &.REFUND:hover {
    background-color: ${({ theme }) => theme.color.blue[50].value};
  }

  &.INCOME:hover {
    background-color: ${({ theme }) => theme.color.green[50].value};
  }

  .tag {
    margin-bottom: 8px;
  }

  .amount {
    display: flex;
    align-items: center;
    gap: 8px;

    &-unit {
      ${({ theme }) => expandTypography(theme.typography.title.small.bold)};
    }

    &.OUTCOME {
      color: ${({ theme }) => theme.color.peach[600].value};
    }

    &.REFUND {
      color: ${({ theme }) => theme.color.blue[600].value};
    }

    &.INCOME {
      color: ${({ theme }) => theme.color.green[600].value};
    }

    ${({ theme }) => theme.mq.desktop} {
      ${({ theme }) => expandTypography(theme.typography.title.medium.bold)};
    }
  }

  .transaction-cell-section {
    padding: 32px 0;

    > .content {
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)};

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)};
      }
    }

    > .reason {
      opacity: 0.6;
      ${({ theme }) => expandTypography(theme.typography.body.small.regular)};

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) =>
          expandTypography(theme.typography.body.medium.regular)};
      }
    }
  }

  .transaction-cell-footer {
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
      width: 30px;
      height: 30px;
      border-radius: 50%;
      background-color: ${({ theme }) => theme.color.gray[500].value};

      ${({ theme }) => theme.mq.desktop} {
        width: 34px;
        height: 34px;
      }
    }

    &-name {
      ${({ theme }) => expandTypography(theme.typography.body.small.bold)};

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.body.medium.bold)};
      }
    }

    &-createdAt {
      opacity: 0.6;
      ${({ theme }) => expandTypography(theme.typography.body.small2x.regular)};

      ${({ theme }) => theme.mq.desktop} {
        ${({ theme }) => expandTypography(theme.typography.body.small.regular)};
      }
    }
  }

  .ant-avatar {
    background-color: ${({ theme }) =>
      theme.color.bluishGray[500].value} !important;
  }

  .icon-unit {
    opacity: 0.5;
    display: flex;
    ${({ theme }) => expandTypography(theme.typography.body.small2x.medium)};

    ${({ theme }) => theme.mq.desktop} {
      ${({ theme }) => expandTypography(theme.typography.body.small.medium)};
    }

    //TODO: 더 간략하게 css를 줄 수 있을 것 같다
    > .reaction,
    .comment {
      display: flex;
      align-items: center;
    }

    > .reaction > div {
      gap: 2px;
      display: flex;
      align-items: center;
    }

    .reaction-up {
      margin-right: 8px;
    }

    .reaction-down {
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
