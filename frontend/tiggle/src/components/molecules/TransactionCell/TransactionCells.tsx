import Masonry from "react-masonry-css";
import { useSelector } from "react-redux";

import { TransactionRespDto } from "@/generated/models/TransactionRespDto";
import { RootState } from "@/store";
import { TransactionCellsStyle } from "@/styles/components/TransactionCellsStyle";

import TransactionCell from "./TransactionCell";
import TransactionCellSkeleton from "./TransactionCellSkeleton";

interface TransactionCellsProps {
  data: TransactionRespDto[];
}

export default function TransactionCells({ data }: TransactionCellsProps) {
  const breakpointColumnsObj = {
    default: 2,
    767: 1,
  };

  const { isError, isLoading } = useSelector((state: RootState) => state.data);

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
