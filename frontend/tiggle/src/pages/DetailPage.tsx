import { useParams } from "react-router-dom";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import HashTag from "@/components/atoms/HashTag/HashTag";
import TextArea from "@/components/atoms/TextArea/TextArea";
import CommentCell, {
  Comment,
} from "@/components/molecules/CommentCell/CommentCell";
import PostHeader from "@/components/molecules/PostHeader/PostHeader";
import ReactionSection from "@/components/molecules/ReactionSection/ReactionSection";
import {
  DetailPageContentStyle,
  DetailPageReplySectionStyle,
} from "@/styles/pages/DetailPageStyle";
import { Tx } from "@/types";

const DetailPage = () => {
  const id = Number(useParams().id);

  return (
    <>
      <DetailPageContentStyle className="page-container">
        <PostHeader {...headerMock} />

        <div className="divider" />

        <div className="content">
          <div className="content-image">
            <img src={contentMock.imageUrl} alt={headerMock.title} />
          </div>
          <p className="content-text">{contentMock.reason}</p>

          <ul className="content-tags">
            {contentMock.tags.map(tag => (
              <HashTag key={`tag-${tag}`} label={tag} />
            ))}
          </ul>
        </div>

        <ReactionSection
          className="reaction"
          txType={headerMock.txType}
          reactions={reactionMock}
          onAddReaction={reaction => console.log(reaction)}
          onCancelReaction={() => console.log("reaction cancelled")}
        />
      </DetailPageContentStyle>

      <DetailPageReplySectionStyle>
        <div className="page-container">
          <p className="title">댓글 20개</p>
          <div className="input">
            <TextArea placeholder="댓글 남기기" />
            <CTAButton size="md">댓글 등록</CTAButton>
          </div>

          <div className="divider" />

          <div className="comments">
            {commentMock.map(comment => (
              <CommentCell txType={headerMock.txType} comment={comment} />
            ))}
          </div>
        </div>
      </DetailPageReplySectionStyle>
    </>
  );
};

export default DetailPage;

const headerMock = {
  id: 0,
  title: "제목 텍스트",
  amount: 100000,
  txType: Tx.Outcome,
  user: {
    name: "사용자이름",
    profileUrl: "profile.png",
  },
  date: "2023-08-06T06:00:00.000Z",
  category: "카테고리",
  asset: "자산",
};

const contentMock = {
  imageUrl: "https://storybook.js.org/images/develop/time-frame-overview.svg",
  reason:
    "구매 사유 텍스트 텍스트 텍스트 한글 입숨 찬미를 낙원을 천자만홍이 옷을 사막이다. 청춘이 길을 인간의 새가 천자만홍이 힘있다. 있을 속에 작고 있는 되는 그러므로 불어 황금시대다. 얼마나 몸이 놀이 봄바람이다. 거선의 더운지라 온갖 기관과 구하지 오아이스도 황금시대다. ",
  tags: ["해시태그 1", "해시태그 2"],
};

const reactionMock = {
  Up: 100,
  Down: 200,
};

const commentMock: Comment[] = [
  {
    id: 0,
    user: {
      name: "사용자 1",
      profileUrl: "./profile.png",
    },
    createdAt: "2023-08-06T06:00:00.000Z",
    content: "댓글 텍스트 입숨",
    replies: [],
  },
  {
    id: 1,
    user: {
      name: "사용자 2",
      profileUrl: "./profile.png",
    },
    createdAt: "2023-08-06T06:00:00.000Z",
    content: "댓글 텍스트 입숨",
    replies: [
      {
        id: 3,
        user: {
          name: "사용자 3",
          profileUrl: "./profile.png",
        },
        createdAt: "2023-08-06T06:00:00.000Z",
        content: "댓글 텍스트 입숨",
      },
    ],
  },
];
