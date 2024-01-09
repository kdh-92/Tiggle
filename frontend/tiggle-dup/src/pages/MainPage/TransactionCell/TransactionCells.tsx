import Masonry from "react-masonry-css";

import { TransactionRespDto } from "@/generated/models/TransactionRespDto";
import { TransactionCellsStyle } from "@/pages/MainPage/TransactionCell/TransactionCellsStyle";

import TransactionCell from "./TransactionCell";
import TransactionCellSkeleton from "./TransactionCellSkeleton";
import { useAllTransactionsQuery } from "../query";

interface TransactionCellsProps {
  data: TransactionRespDto[];
}

export default function TransactionCells({ data }: TransactionCellsProps) {
  const breakpointColumnsObj = {
    default: 2,
    767: 1,
  };

  const { isError, isLoading } = useAllTransactionsQuery();

  return (
    <>
      <TransactionCellsStyle>
        <Masonry
          breakpointCols={breakpointColumnsObj}
          className="transaction-cell-box-masonry"
          columnClassName="transaction-cells"
        >
          {isLoading &&
            [...Array(4)].map((_, i) => <TransactionCellSkeleton key={i} />)}
          {!isLoading &&
            !isError &&
            data?.map(el => {
              return <TransactionCell key={el.id} {...el} />;
            })}
          {isError && <p>An error occurred.</p>}
        </Masonry>
      </TransactionCellsStyle>
    </>
  );
}
