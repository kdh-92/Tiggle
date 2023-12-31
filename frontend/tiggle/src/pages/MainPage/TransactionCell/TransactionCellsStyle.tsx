import styled from "styled-components";

export const TransactionCellsStyle = styled.div`
  padding: 0 24px;
  margin-top: 16px;

  ${({ theme }) => theme.mq.desktop} {
    display: flex;
    justify-content: center;
    padding: 0 32px;
  }

  .transaction-cell-box-masonry {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    width: auto;
    gap: 24px;

    ${({ theme }) => theme.mq.desktop} {
      display: flex;
      justify-content: center;
      gap: 24px;
    }
  }

  .transaction-cells {
    background-clip: padding-box;
  }
`;
