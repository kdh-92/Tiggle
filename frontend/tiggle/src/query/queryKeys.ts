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

export const statisticsKeys = {
  key: "statistics" as const,
  weekly: (weekOffset: number) =>
    [statisticsKeys.key, "weekly", weekOffset] as const,
  monthly: (year?: number, month?: number) =>
    [statisticsKeys.key, "monthly", { year, month }] as const,
};

export const characterKeys = {
  key: "character" as const,
  me: () => [characterKeys.key, "me"] as const,
  detail: (memberId: number) =>
    [characterKeys.key, "detail", memberId] as const,
  catalog: () => [characterKeys.key, "catalog"] as const,
};

export const itemKeys = {
  key: "item" as const,
  inventory: () => [itemKeys.key, "inventory"] as const,
  catalog: () => [itemKeys.key, "catalog"] as const,
  equipment: () => [itemKeys.key, "equipment", "me"] as const,
  memberEquipment: (memberId: number) =>
    [itemKeys.key, "equipment", memberId] as const,
};

export const achievementKeys = {
  key: "achievement" as const,
  lists: () => [achievementKeys.key, "list"] as const,
  recent: (limit: number) => [achievementKeys.key, "recent", limit] as const,
};

export const challengeKeys = {
  key: "challenge" as const,
  active: () => [challengeKeys.key, "active"] as const,
  detail: (id: number) => [challengeKeys.key, "detail", id] as const,
  history: (page: number) => [challengeKeys.key, "history", page] as const,
};
