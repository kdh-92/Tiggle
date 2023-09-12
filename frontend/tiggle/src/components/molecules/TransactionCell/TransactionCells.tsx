import Masonry from "react-masonry-css";

import { TransactionCellsStyle } from "@/styles/components/TransactionCellsStyle";

import TransactionCell, { TransactionCellProps } from "./TransactionCell";

export default function Feeds({
  dataList,
}: {
  dataList: TransactionCellProps[];
}) {
  const breakpointColumnsObj = {
    default: 2,
    767: 1,
  };

  return (
    <TransactionCellsStyle>
      <Masonry
        breakpointCols={breakpointColumnsObj}
        className="transaction-cell-box-masonry"
        columnClassName="transaction-cells"
      >
        {dataList?.map(el => {
          return <TransactionCell key={el.id} {...el} />;
        })}
      </Masonry>
    </TransactionCellsStyle>
  );
}
