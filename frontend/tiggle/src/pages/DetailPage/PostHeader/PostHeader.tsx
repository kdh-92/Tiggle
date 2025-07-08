import { useNavigate } from "react-router-dom";

import { useMutation, useQueryClient } from "@tanstack/react-query";
import cn from "classnames";
import dayjs from "dayjs";

import { Menu, MenuItem } from "@/components/atoms";
import TypeTag from "@/components/atoms/TypeTag/TypeTag";
import {
  CategoryRespDto,
  MemberRespDto,
  TransactionRespDto,
  TransactionApiControllerService,
} from "@/generated";
import useAuth from "@/hooks/useAuth";
import useMessage from "@/hooks/useMessage";
import {
  PostHeaderStyle,
  StyledPostHeaderDetail,
  StyledPostHeaderTitle,
} from "@/pages/DetailPage/PostHeader/PostHeaderStyle";
import { transactionKeys } from "@/query/queryKeys";
import { convertTxTypeToWord } from "@/utils/txType";

export interface PostHeaderProps
  extends Pick<TransactionRespDto, "id" | "content" | "amount" | "date"> {
  // TODO: api response 변경된 후, TransactionDto 에서 Pick 하는 것으로 수정
  sender: MemberRespDto;
  category: CategoryRespDto;
  // asset: string;
}

export default function PostHeader({
  id,
  content,
  amount,
  date,
  sender,
  category,
  // asset,
}: PostHeaderProps) {
  const txType = "OUTCOME";
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const messageApi = useMessage();
  const { profile } = useAuth();

  const isOwner = profile?.data?.id === sender.id;

  const deleteTransactionMutation = useMutation({
    mutationFn: (transactionId: number) =>
      TransactionApiControllerService.deleteTransaction(transactionId),
    onSuccess: () => {
      messageApi.open({
        type: "success",
        content: "거래가 삭제되었습니다.",
      });

      queryClient.invalidateQueries({
        queryKey: transactionKeys.lists(),
      });
      queryClient.invalidateQueries({
        queryKey: ["transactions"],
      });
      queryClient.invalidateQueries({
        queryKey: transactionKeys.detail(id),
      });

      navigate("/");
    },
    onError: error => {
      console.error("거래 삭제 실패:", error);
      messageApi.open({
        type: "error",
        content: "거래 삭제에 실패했습니다.",
      });
    },
  });

  const handleEdit = () => {
    navigate(`/create/edit/${id}`);
  };

  const handleDelete = () => {
    const isConfirmed = window.confirm(
      "정말로 이 거래를 삭제하시겠습니까?\n삭제된 거래는 복구할 수 없습니다.",
    );

    if (isConfirmed) {
      deleteTransactionMutation.mutate(id);
    }
  };

  return (
    <PostHeaderStyle id={`post-header-${id}`}>
      <StyledPostHeaderTitle>
        <TypeTag className="tag" txType={txType} size={"md"} />
        <div className={cn("amount", txType)}>
          <span className="amount-unit">₩</span>
          <span className="amount-number">
            {amount.toLocaleString("ko-KR")}
          </span>
        </div>
        <p className="title">{content}</p>
      </StyledPostHeaderTitle>

      <StyledPostHeaderDetail>
        <div className="user">
          <img
            className="user-profile"
            alt="user profile"
            src={sender.profileUrl ?? "/assets/user-placeholder.png"}
          />
          <p className="user-name">{sender.nickname}</p>
        </div>
        <div className="item-wrapper">
          <div className="item date">
            <p className="item-title">{convertTxTypeToWord(txType)}일자</p>
            <p className="item-data">{dayjs(date).format("YYYY.MM.DD")}</p>
          </div>
          {/*<div className="item">*/}
          {/*  <p className="item-title">자산</p>*/}
          {/*  <p className="item-data">{asset}</p>*/}
          {/*</div>*/}
          <div className="item">
            <p className="item-title">카테고리</p>
            <p className="item-data">{category.name}</p>
          </div>
        </div>
      </StyledPostHeaderDetail>

      {isOwner && (
        <Menu className="post-header-menu">
          <MenuItem
            label="수정하기"
            onClick={handleEdit}
            disabled={deleteTransactionMutation.isLoading}
          />
          <MenuItem
            label="삭제하기"
            onClick={handleDelete}
            disabled={deleteTransactionMutation.isLoading}
          />
        </Menu>
      )}
    </PostHeaderStyle>
  );
}
