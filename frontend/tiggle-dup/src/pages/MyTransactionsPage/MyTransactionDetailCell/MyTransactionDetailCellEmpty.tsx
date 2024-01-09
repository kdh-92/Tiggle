import { Archive } from "react-feather";

import { MyTransactionDetailCellEmptyStyle } from "./MyTransactionDetailCellStyle";

const MyTransactionDetailCellEmpty = () => {
  return (
    <MyTransactionDetailCellEmptyStyle>
      <Archive />
      <p>조건에 맞는 데이터가 없습니다.</p>
    </MyTransactionDetailCellEmptyStyle>
  );
};

export default MyTransactionDetailCellEmpty;
