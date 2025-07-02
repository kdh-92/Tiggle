/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { TransactionRespDto } from './TransactionRespDto';

export type TransactionPageRespDto = {
    transactions: Array<TransactionRespDto>;
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    isLast: boolean;
};
