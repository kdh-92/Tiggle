import { LoaderFunctionArgs, useLoaderData, useParams } from "react-router-dom";

import { QueryClient, useQuery } from "@tanstack/react-query";

import { HashTag } from "@/components/atoms";
import {
  CategoryRespDto,
  ReactionApiService,
  TransactionApiControllerService,
} from "@/generated";
import CommentCell from "@/pages/DetailPage/CommentCell/CommentCell";
import CommentForm from "@/pages/DetailPage/CommentForm/CommentForm";
import {
  DetailPageStyle,
  DetailPageContentStyle,
  DetailPageCommentSectionStyle,
} from "@/pages/DetailPage/DetailPageStyle";
import PostHeader from "@/pages/DetailPage/PostHeader/PostHeader";
import ReactionSection from "@/pages/DetailPage/ReactionSection/ReactionSection";
import { commentKeys, reactionKeys, transactionKeys } from "@/query/queryKeys";

const transactionQuery = (id: number) => ({
  queryKey: transactionKeys.detail(id),
  queryFn: async () => TransactionApiControllerService.getTransaction(id),
});

export const detailPageLoader =
  (queryClient: QueryClient) =>
  ({ params }: LoaderFunctionArgs) =>
    queryClient.ensureQueryData(transactionQuery(Number(params.id)));

const DetailPage = () => {
  const id = Number(useParams().id);
  const initialData = useLoaderData() as Awaited<
    ReturnType<ReturnType<typeof detailPageLoader>>
  >;

  const { data: transactionData } = useQuery({
    ...transactionQuery(id),
    initialData,
  });
  const { data: reactionData } = useQuery({
    queryKey: reactionKeys.detail(id),
    queryFn: async () => ReactionApiService.getReactionSummary(id),
  });
  const { data: commentsData } = useQuery({
    queryKey: commentKeys.list(id),
    queryFn: async () => TransactionApiControllerService.getAllCommentsByTx(id),
  });

  if (!transactionData?.data) {
    return <div>Loading...</div>;
  }

  return (
    <DetailPageStyle className="page-container">
      <section className="content">
        <PostHeader
          {...transactionData.data}
          sender={transactionData.data.member}
          // asset={transactionData.asset!.name!}
          // category={transactionData.category!.name!}
          category={transactionData.data.category as CategoryRespDto}
        />

        <DetailPageContentStyle>
          <div className="image">
            <img
              src={
                transactionData.data.imageUrl
                  ? `${import.meta.env.VITE_API_URL}${transactionData.data.imageUrl}`
                  : "/assets/img-placeholder.png"
              }
              alt={transactionData.data.content}
            />
          </div>
          <div className="content">
            <p className="content-reason">{transactionData.data.reason}</p>
            <ul className="content-tags">
              {transactionData.data.tagNames?.map(tag => (
                <HashTag key={`tag-${tag}`} label={tag} />
              ))}
            </ul>
          </div>
        </DetailPageContentStyle>

        {reactionData?.data && (
          <ReactionSection
            {...reactionData.data}
            txId={id}
            className="reaction"
          />
        )}
      </section>

      <DetailPageCommentSectionStyle className="comment">
        <div className="title">
          <p className="main">댓글</p>
          <p className="sub">{reactionData?.data?.commentCount}개</p>
        </div>

        <div className="comment-cards">
          {commentsData?.data?.comments?.map(comment => (
            <CommentCell
              {...comment}
              childCount={comment.childCommentCount}
              key={`comment-cell-${comment.id}`}
            />
          ))}
        </div>

        <CommentForm txId={id} />
      </DetailPageCommentSectionStyle>
    </DetailPageStyle>
  );
};

export default DetailPage;
