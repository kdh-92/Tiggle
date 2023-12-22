import { useLayoutEffect } from "react";
import { useDispatch } from "react-redux";
import { LoaderFunctionArgs, useLoaderData, useParams } from "react-router-dom";

import { QueryClient } from "@tanstack/react-query";

import HashTag from "@/components/atoms/HashTag/HashTag";
import CommentCell from "@/pages/DetailPage/CommentCell/CommentCell";
import CommentForm from "@/pages/DetailPage/CommentForm/CommentForm";
import {
  DetailPageStyle,
  DetailPageContentStyle,
  DetailPageCommentSectionStyle,
} from "@/pages/DetailPage/DetailPageStyle";
import PostHeader from "@/pages/DetailPage/PostHeader/PostHeader";
import ReactionSection from "@/pages/DetailPage/ReactionSection/ReactionSection";
import store from "@/store/detailPage";

import {
  transactionQuery,
  useCommentsQuery,
  useReactionQuery,
  useTransactionQuery,
} from "./query";

export const loader =
  (queryClient: QueryClient) =>
  ({ params }: LoaderFunctionArgs) =>
    queryClient.ensureQueryData(transactionQuery(Number(params.id)));

const DetailPage = () => {
  const id = Number(useParams().id);
  const initialData = useLoaderData() as Awaited<
    ReturnType<ReturnType<typeof loader>>
  >;

  const { data: transactionData } = useTransactionQuery(id, { initialData });
  const { data: reactionData } = useReactionQuery(id);
  const { data: commentsData } = useCommentsQuery(id);

  const dispatch = useDispatch();
  useLayoutEffect(() => {
    dispatch(store.actions.creators.setType(transactionData.type));
  }, [transactionData]);

  return (
    <DetailPageStyle className="page-container">
      <section className="content">
        <PostHeader
          {...transactionData}
          sender={{
            nickname: "사용자1",
          }}
          asset={transactionData.asset.name}
          category={transactionData.category.name}
        />

        <DetailPageContentStyle>
          <div className="image">
            <img
              src={transactionData.imageUrl ?? "/assets/img-placeholder.png"}
              alt={transactionData.content}
            />
          </div>
          <div className="content">
            <p className="content-reason">{transactionData.reason}</p>
            <ul className="content-tags">
              {transactionData.txTagNames
                ?.split(",")
                .map(tag => <HashTag key={`tag-${tag}`} label={tag} />)}
            </ul>
          </div>
        </DetailPageContentStyle>

        {reactionData && (
          <ReactionSection {...reactionData} txId={id} className="reaction" />
        )}
      </section>

      <DetailPageCommentSectionStyle className="comment">
        <div className="title">
          <p className="main">댓글</p>
          <p className="sub">{reactionData?.commentCount}개</p>
        </div>

        <div className="comment-cards">
          {commentsData?.content?.map(comment => (
            <CommentCell
              {...comment}
              key={`comment-cell-${comment.id}`}
              receiverId={transactionData.member?.id}
            />
          ))}
        </div>

        <CommentForm txId={id} receiverId={transactionData.member?.id} />
      </DetailPageCommentSectionStyle>
    </DetailPageStyle>
  );
};

export default DetailPage;
