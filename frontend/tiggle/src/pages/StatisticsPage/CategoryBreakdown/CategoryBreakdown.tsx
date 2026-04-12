import type { CategoryBreakdownDto } from "@/types/gamification";

import { CategoryBreakdownWrapper } from "./CategoryBreakdownStyle";

interface CategoryBreakdownProps {
  data: CategoryBreakdownDto[];
}

const formatAmount = (amount: number): string =>
  `${amount.toLocaleString("ko-KR")}원`;

const CategoryBreakdown = ({ data }: CategoryBreakdownProps) => {
  const sorted = [...data].sort((a, b) => b.ratio - a.ratio);

  return (
    <CategoryBreakdownWrapper>
      <p className="section-title">카테고리별 지출</p>

      {sorted.length > 0 ? (
        <div className="category-list">
          {sorted.map(item => (
            <div key={item.categoryId} className="category-item">
              <div className="category-header">
                <span className="category-name">{item.categoryName}</span>
                <div className="category-info">
                  <span className="category-amount">
                    {formatAmount(item.amount)}
                  </span>
                  <span className="category-ratio">
                    {item.ratio.toFixed(1)}%
                  </span>
                </div>
              </div>
              <div className="bar-track">
                <div
                  className="bar-fill"
                  style={{ width: `${Math.min(item.ratio, 100)}%` }}
                />
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p className="empty-message">이번 달 지출 데이터가 없습니다.</p>
      )}
    </CategoryBreakdownWrapper>
  );
};

export default CategoryBreakdown;
