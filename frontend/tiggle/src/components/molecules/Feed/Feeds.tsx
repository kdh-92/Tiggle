import Masonry from "react-masonry-css";

import { FeedsStyle } from "@/styles/components/FeedsStyle";

import Feed, { FeedProps } from "./Feed";

export default function Feeds({ dataList }: { dataList: FeedProps[] }) {
  const breakpointColumnsObj = {
    default: 2,
    767: 1,
  };

  return (
    <FeedsStyle>
      <Masonry
        breakpointCols={breakpointColumnsObj}
        className="feed-box-masonry"
        columnClassName="feeds"
      >
        {dataList?.map(el => {
          return <Feed key={el.id} {...el} />;
        })}
      </Masonry>
    </FeedsStyle>
  );
}
