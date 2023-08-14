import Masonry from "react-masonry-css";

import Feed, { FeedProps } from "./Feed";

export default function Feeds({ dataList }: { dataList: FeedProps[] }) {
  const breakpointColumnsObj = {
    default: 2,
    767: 1,
  };

  return (
    <Masonry
      breakpointCols={breakpointColumnsObj}
      className="feed-box-masonry"
      columnClassName="feeds"
    >
      {dataList &&
        dataList.map(el => {
          return <Feed key={el.id} {...el} />;
        })}
    </Masonry>
  );
}
