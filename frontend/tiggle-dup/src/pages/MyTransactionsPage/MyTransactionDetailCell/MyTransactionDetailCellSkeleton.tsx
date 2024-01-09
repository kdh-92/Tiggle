import { ForwardedRef, forwardRef } from "react";

import { MyTransactionDetailCellSkeletonStyle } from "./MyTransactionDetailCellStyle";

const MyTransactionDetailCellSkeleton = forwardRef(
  (_, ref: ForwardedRef<HTMLDivElement>) => {
    return (
      <MyTransactionDetailCellSkeletonStyle ref={ref}>
        <div className="header animated-skeleton">
          <div className="tag" />
          <div className="amount" />
        </div>
        <div className="body animated-skeleton">
          <div className="content" />
          <div className="reason">
            <div className="reason-line line-1" />
            <div className="reason-line line-2" />
          </div>
        </div>
      </MyTransactionDetailCellSkeletonStyle>
    );
  },
);

export default MyTransactionDetailCellSkeleton;
