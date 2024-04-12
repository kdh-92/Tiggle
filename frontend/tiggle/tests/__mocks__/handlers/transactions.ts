import { http, HttpResponse } from "msw";

import { getCountOffsetTxResp } from "../response/transaction";

const getCountOffsetTx = http.get(
  `${import.meta.env.VITE_API_URL}api/v1/transaction`,
  () => {
    return HttpResponse.json(getCountOffsetTxResp, { status: 200 });
  },
);

const getAllTx = http.get(
  `${import.meta.env.VITE_API_URL}api/v1/transaction/all`,
  () => {
    return HttpResponse.json(getCountOffsetTxResp, { status: 200 });
  },
);

export default [getCountOffsetTx, getAllTx];
