export const transactionKeys = {
  key: "transaction" as const,
  lists: () => [transactionKeys.key, "list"] as const, // get all
  list: (
    filter: Record<string, any>, // get tx by page, get tx by member
  ) => [transactionKeys.lists(), filter] as const,
  details: () => [transactionKeys.key, "detail"] as const,
  detail: (id: number) => [transactionKeys.details(), id] as const, //get detail by id
};

export const commentKeys = {
  key: "comment" as const,
  lists: () => [commentKeys.key, "list"] as const,
  list: (txId: number) => [...commentKeys.lists(), txId] as const,
  replies: () => [commentKeys.key, "reply"] as const,
  reply: (commentId: number) => [...commentKeys.replies(), commentId] as const,
};

export const reactionKeys = {
  key: "reaction" as const,
  details: () => [reactionKeys.key, "detail"] as const,
  detail: (id: number) => [...reactionKeys.details(), id] as const,
};

export const assetKeys = {
  key: "asset" as const,
  lists: () => [assetKeys.key, "list"] as const,
  list: (filter: Record<string, any>) =>
    [...assetKeys.lists(), filter] as const,
  details: () => [assetKeys.key, "detail"] as const,
  detail: (id: number) => [...assetKeys.details(), id] as const,
};

export const categoryKeys = {
  key: "category" as const,
  lists: () => [categoryKeys.key, "list"] as const,
  list: (filter?: Record<string, any>) =>
    [...categoryKeys.lists(), filter] as const,
  details: () => [categoryKeys.key, "detail"] as const,
  detail: (id: number) => [...categoryKeys.details(), id] as const,
};

export const tagKeys = {
  key: "tag" as const,
  lists: () => [tagKeys.key, "list"] as const,
  list: (filter: Record<string, any>) => [...tagKeys.lists(), filter] as const,
  details: () => [tagKeys.key, "detail"] as const,
  detail: (id: number) => [...tagKeys.details(), id] as const,
};

export const memberKeys = {
  key: "member" as const,
  details: () => [memberKeys.key, "detail"] as const,
  detail: (id: number | "me") => [...memberKeys.details(), id] as const,
};
