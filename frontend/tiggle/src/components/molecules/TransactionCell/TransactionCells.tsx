import Masonry from "react-masonry-css";
import { useSelector } from "react-redux";

import Loading from "@/components/atoms/Loading/Loading";
import { TransactionRespDto } from "@/generated/models/TransactionRespDto";
import { RootState } from "@/store";
import { TransactionCellsStyle } from "@/styles/components/TransactionCellsStyle";

import TransactionCell from "./TransactionCell";

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
      {isLoading && <Loading />}
      {isError && <p>An error occurred.</p>}
      <TransactionCellsStyle>
        <Masonry
          breakpointCols={breakpointColumnsObj}
          className="transaction-cell-box-masonry"
          columnClassName="transaction-cells"
        >
          {!isLoading &&
            !isError &&
            data?.map(el => {
              return <TransactionCell key={el.id} {...el} />;
            })}
        </Masonry>
      </TransactionCellsStyle>
    </>
  );
}
