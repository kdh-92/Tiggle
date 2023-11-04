import { useLayoutEffect } from "react";
import { useDispatch } from "react-redux";
import { LoaderFunctionArgs, useLoaderData, useParams } from "react-router-dom";

import { QueryClient, useQuery } from "@tanstack/react-query";

import HashTag from "@/components/atoms/HashTag/HashTag";
import CommentCell from "@/components/molecules/CommentCell/CommentCell";
import CommentForm from "@/components/molecules/CommentForm/CommentForm";
import PostHeader from "@/components/molecules/PostHeader/PostHeader";
import ReactionSection from "@/components/molecules/ReactionSection/ReactionSection";
import {
  ReactionApiService,
  TransactionApiControllerService,
} from "@/generated";
import store from "@/store/detailPage";
import {
  DetailPageStyle,
  DetailPageContentStyle,
  DetailPageCommentSectionStyle,
} from "@/styles/pages/DetailPageStyle";

const transactionQuery = (id: number) => ({
  queryKey: ["transaction", "detail", id],
  queryFn: async () => TransactionApiControllerService.getTransaction(id),
});

export const loader =
  (queryClient: QueryClient) =>
  ({ params }: LoaderFunctionArgs) =>
    queryClient.ensureQueryData(transactionQuery(Number(params.id)));

const DetailPage = () => {
  const id = Number(useParams().id);
  const initialData = useLoaderData() as Awaited<
    ReturnType<ReturnType<typeof loader>>
  >;

  // initial fetch
  const { data: transactionData } = useQuery({
    ...transactionQuery(id),
    initialData,
  });

  // lazy fetch
  const { data: reactionData } = useQuery({
    queryKey: ["reaction", "detail", id],
    queryFn: async () => ReactionApiService.getReactionSummary(id),
  });
  const { data: commentsData } = useQuery({
    queryKey: ["transaction", "comments", id],
    queryFn: async () => TransactionApiControllerService.getAllCommentsByTx(id),
  });

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
          asset={"asset"}
          category={"category"}
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
