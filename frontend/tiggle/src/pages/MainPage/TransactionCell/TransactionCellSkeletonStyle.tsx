import { styled } from "styled-components";

export const TransactionCellSkeletonStyle = styled.div`
  display: grid;
  gap: 32px;
  margin-bottom: 24px;
  border-radius: 24px;
  background-color: ${({ theme: { color } }) => color.white.value};
  padding: 24px;

  ${({ theme }) => theme.mq.desktop} {
    width: 327px;
  }

  .cell-title-skeleton {
    display: grid;
    gap: 8px;

    .amount-skeleton {
      width: 36px;
      height: 18px;
      border-radius: 8px;
      background-color: ${({ theme: { color } }) =>
        color.bluishGray[200].value};
    }

    .transaction-section-skeleton {
      width: 95px;
      height: 26px;
      border-radius: 8px;
      background-color: ${({ theme: { color } }) =>
        color.bluishGray[200].value};
    }
  }

  .cell-body-skeleton {
    display: grid;
    gap: 8px;

    .skeleton-body-title {
      width: 149px;
      height: 22px;
      border-radius: 8px;
      background-color: ${({ theme: { color } }) =>
        color.bluishGray[200].value};
    }

    .skeleton-body-sub {
      display: grid;
      gap: 4px;

      .skeleton-sub-one {
        width: 265px;
        height: 14px;
        border-radius: 6px;
        background-color: ${({ theme: { color } }) =>
          color.bluishGray[200].value};
      }

      .skeleton-sub-two {
        width: 200px;
        height: 14px;
        border-radius: 6px;
        background-color: ${({ theme: { color } }) =>
          color.bluishGray[200].value};
      }
    }
  }

  .cell-footer-skeleton {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    .info-skeleton {
      display: flex;
      gap: 8px;

      .skeleton-img {
        width: 30px;
        height: 30px;
        border-radius: 20px;
        background-color: ${({ theme: { color } }) =>
          color.bluishGray[200].value};
      }

      .skeleton-user {
        display: grid;
        gap: 2px;

        .skeleton-name {
          width: 55px;
          height: 14px;
          border-radius: 6px;
          background-color: ${({ theme: { color } }) =>
            color.bluishGray[200].value};
        }

        .skeleton-createdAt {
          width: 35px;
          height: 10px;
          border-radius: 6px;
          background-color: ${({ theme: { color } }) =>
            color.bluishGray[200].value};
        }
      }
    }

    .skeleton-icon {
      display: flex;
      gap: 8px;

      .icon {
        width: 26px;
        height: 14px;
        border-radius: 6px;
        background-color: ${({ theme: { color } }) =>
          color.bluishGray[200].value};
      }
    }
  }

  .animated-skeleton {
    animation: pulse 1.5s infinite;
  }

  @keyframes pulse {
    0% {
      opacity: 1;
    }
    50% {
      opacity: 0.5;
    }
    100% {
      opacity: 1;
    }
  }
`;
