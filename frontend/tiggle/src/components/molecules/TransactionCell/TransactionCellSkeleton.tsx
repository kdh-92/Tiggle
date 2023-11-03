import { TransactionCellSkeletonStyle } from "@/styles/components/TransactionCellSkeletonStyle";

export default function TransactionCellSkeleton() {
  return (
    <TransactionCellSkeletonStyle>
      <div className="cell-title-skeleton animated-skeleton">
        <div className="amount-skeleton"></div>
        <div className="transaction-section-skeleton"></div>
      </div>
      <div className="cell-body-skeleton animated-skeleton">
        <div className="skeleton-body-title"></div>
        <div className="skeleton-body-sub">
          <div className="skeleton-sub-one"></div>
          <div className="skeleton-sub-two"></div>
        </div>
      </div>
      <div className="cell-footer-skeleton animated-skeleton">
        <div className="info-skeleton">
          <div className="skeleton-img"></div>
          <div className="skeleton-user">
            <div className="skeleton-name"></div>
            <div className="skeleton-createdAt"></div>
          </div>
        </div>
        <div className="skeleton-icon">
          <div className="icon"></div>
          <div className="icon"></div>
          <div className="icon"></div>
        </div>
      </div>
    </TransactionCellSkeletonStyle>
  );
}
