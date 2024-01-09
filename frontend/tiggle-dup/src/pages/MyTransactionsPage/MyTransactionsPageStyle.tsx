import styled from "styled-components";

export const FilteBoxStyle = styled.form`
  margin-bottom: 40px;
  display: flex;
  flex-direction: column;
  gap: 8px;

  .filter-item {
    border-radius: 12px;
    background-color: ${({ theme }) => theme.color.bluishGray[100].value};
    color: ${({ theme }) => theme.color.bluishGray[700].value};
  }
`;

export const MyTransactionCellsStyle = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;
