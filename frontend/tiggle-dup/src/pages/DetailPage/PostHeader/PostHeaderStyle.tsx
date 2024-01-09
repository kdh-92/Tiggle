import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const PostHeaderStyle = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 54px;

  position: relative;
  box-sizing: border-box;

  .post-header-menu {
    position: absolute;
    top: -8px;
    right: 0;
  }

  ${({ theme }) => theme.mq.desktop} {
    width: 100%;
    gap: 72px;
  }
`;

export const StyledPostHeaderTitle = styled.div`
  .tag {
    margin-bottom: 16px;
  }

  .amount {
    margin-bottom: 4px;
    display: flex;
    align-items: center;
    gap: 8px;

    &-unit {
      ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
    }
    &-number {
      ${({ theme }) => expandTypography(theme.typography.title.large2x.bold)}
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
  }

  .title {
    ${({ theme }) => expandTypography(theme.typography.title.small.bold)}
  }

  ${({ theme }) => theme.mq.desktop} {
    .tag {
      margin-bottom: 20px;
    }
    .amount {
      &-unit {
        ${({ theme }) => expandTypography(theme.typography.title.large.bold)}
      }
      &-number {
        ${({ theme }) => expandTypography(theme.typography.title.large3x.bold)}
      }
    }
    .title {
      ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
    }
  }
`;

export const StyledPostHeaderDetail = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;

  .user {
    display: flex;
    align-items: center;
    gap: 8px;
    &-profile {
      width: 20px;
      height: 20px;
      border-radius: 50%;
      background-color: ${({ theme }) => theme.color.bluishGray[300].value};
    }
    &-name {
      ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
      color: ${({ theme }) => theme.color.bluishGray[700].value}
    }
  }

  .item-wrapper {
    display: flex;
    gap: 16px;
  }

  .item {
    min-width: 80px;
    &.date {
      min-width: 108px;
    }
    &-title {
      ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
      color: ${({ theme }) => theme.color.bluishGray[400].value};
    }
    &-data {
      ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
      color: ${({ theme }) => theme.color.bluishGray[700].value};
    }
  }

  ${({ theme }) => theme.mq.desktop} {
    flex-direction: row;
    justify-content: space-between;

    .user-profile {
      width: 24px;
      height: 24px;
    }
    .user-name {
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    }
    .item {
      &-title {
        ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
      }
      &-data {
        ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
      }
    }
  }
`;
