import Masonry from "react-masonry-css";

import { useQuery } from "@tanstack/react-query";

import { TransactionApiControllerService } from "@/generated";
import { TransactionRespDto } from "@/generated/models/TransactionRespDto";
import { TransactionCellsStyle } from "@/pages/MainPage/TransactionCell/TransactionCellsStyle";
import { transactionKeys } from "@/query/queryKeys";

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

  const { isError, isLoading } = useQuery({
    queryKey: transactionKeys.lists(),
    queryFn: () => TransactionApiControllerService.getAllTransaction(),
  });

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
            data?.transactions?.map(el => {
              return <TransactionCell key={el.id} {...el} />;
            })}
          {isError && <p>An error occurred.</p>}
        </Masonry>
      </TransactionCellsStyle>
    </>
  );
}
