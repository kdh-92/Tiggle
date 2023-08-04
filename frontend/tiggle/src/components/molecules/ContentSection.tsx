import Navigation from "./Navigation";
import TitleButton from "../atoms/TitleButton";

export default function ContentSection() {
  return (
    <div className="content-wrap">
      <div className="content-title-wrap">
        <div className="content-title">
          <p>티끌 모아 태산 ⛰</p>
          <p>지출을 기록하고, 조언을 받아보세요!</p>
          <TitleButton />
        </div>
      </div>
      <Navigation />
      <div className="feed-wrap">
        <div className="feed-box-grid">
          <div className="feed">
            <p>티끌 모아 태산 ⛰</p>
            <p>티끌 모아 태산 ⛰</p>
            <p>티끌 모아 태산 ⛰</p>
          </div>
          <div className="feed">
            <p>티끌 모아 태산 ⛰</p>
          </div>
          <div className="feed">
            <p>티끌 모아 태산 ⛰</p>
            <p>티끌 모아 태산 ⛰</p>
          </div>
        </div>
      </div>
    </div>
  );
}
