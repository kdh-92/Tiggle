import { useMemo } from "react";
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
import {
  DetailPageContentStyle,
  DetailPageReplySectionStyle,
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

  // TODO: Redux Store를 이용하여 페이지 단위 변수로 관리
  const txType = useMemo(() => transactionData.type, [transactionData]);

  return (
    <>
      <DetailPageContentStyle className="page-container">
        <PostHeader
          {...transactionData}
          sender={{
            nickname: "사용자1",
          }}
          asset={"asset"}
          category={"category"}
        />

        <div className="divider" />

        <div className="content">
          <div className="content-image">
            <img
              src={transactionData.imageUrl ?? "/assets/img-placeholder.png"}
              alt={transactionData.content}
            />
          </div>
          <p className="content-text">{transactionData.reason}</p>

          <ul className="content-tags">
            {transactionData.txTagNames
              ?.split(",")
              .map(tag => <HashTag key={`tag-${tag}`} label={tag} />)}
          </ul>
        </div>

        {reactionData && (
          <ReactionSection
            {...reactionData}
            type={txType}
            txId={id}
            className="reaction"
          />
        )}
      </DetailPageContentStyle>

      <DetailPageReplySectionStyle>
        <div className="page-container">
          <p className="title">댓글 {reactionData?.commentCount}개</p>
          <CommentForm txId={id} receiverId={transactionData.member?.id} />
          <div className="divider" />
          <div className="comments">
            {commentsData?.content?.map(comment => (
              <CommentCell
                {...comment}
                key={`comment-cell-${comment.id}`}
                type={txType}
                receiverId={transactionData.member?.id}
              />
            ))}
          </div>
        </div>
      </DetailPageReplySectionStyle>
    </>
  );
};

export default DetailPage;
