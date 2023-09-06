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
        className="feed-box-masonry"
        columnClassName="feeds"
      >
        {dataList?.map(el => {
          return <TransactionCell key={el.id} {...el} />;
        })}
      </Masonry>
    </TransactionCellsStyle>
  );
}
