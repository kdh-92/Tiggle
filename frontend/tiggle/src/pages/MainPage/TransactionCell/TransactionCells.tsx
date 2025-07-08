import Masonry from "react-masonry-css";

import { TransactionDtoWithCount } from "@/generated/models/TransactionDtoWithCount";
import { TransactionCellsStyle } from "@/pages/MainPage/TransactionCell/TransactionCellsStyle";

import TransactionCell from "./TransactionCell";
import TransactionCellSkeleton from "./TransactionCellSkeleton";

interface TransactionCellsProps {
  data: TransactionDtoWithCount[] | undefined;
  isLoading?: boolean;
  isError?: boolean;
}

export default function TransactionCells({
  data,
  isLoading = false,
  isError = false,
}: TransactionCellsProps) {
  const breakpointColumnsObj = {
    default: 2,
    767: 1,
  };

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
            data?.map(transaction => {
              return (
                <TransactionCell key={transaction.dto.id} {...transaction} />
              );
            })}
          {isError && <p>An error occurred.</p>}
        </Masonry>
      </TransactionCellsStyle>
    </>
  );
}
