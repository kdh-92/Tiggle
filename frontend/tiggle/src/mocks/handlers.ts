import { rest } from "msw";

const getTxDetailHandler = rest.get(
  `${process.env.REACT_APP_API_URL}/transaction/:id`,
  (_, res, ctx) =>
    res(
      ctx.status(200),
      ctx.json({
        memberId: 0,
        parentId: 0,
        type: "INCOME",
        imageUrl: "string",
        amount: 0,
        date: "2023-08-13",
        content: "string",
        reason: "string",
        id: 0,
        parentTx: {
          memberId: 0,
          parentId: 0,
          type: "INCOME",
          imageUrl: "string",
          amount: 0,
          date: "2023-08-13",
          content: "string",
          reason: "string",
        },
        createdAt: "2023-08-13T14:15:07.466Z",
      }),
    ),
);

export const handlers = [getTxDetailHandler];
