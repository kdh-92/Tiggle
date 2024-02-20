import { http, HttpResponse } from 'msw'
import { getCountOffsetTxResp, getAllTxResp } from '../response/transaction'

const getCountOffsetTx = http.get(`${process.env.VITE_API_URL}api/v1/transaction`, () => {
  return HttpResponse.json(getCountOffsetTxResp, { status: 200 })
});

const getAllTx = http.get(`${process.env.VITE_API_URL}api/v1/transaction/all`, () => {
  return HttpResponse.json(getCountOffsetTxResp, { status: 200 })
});

export default [getCountOffsetTx, getAllTx];
