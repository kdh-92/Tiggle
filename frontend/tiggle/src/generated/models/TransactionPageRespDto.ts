/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { TransactionDtoWithCount } from './TransactionDtoWithCount';

export type TransactionPageRespDto = {
    transactions: Array<TransactionDtoWithCount>;
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    isLast: boolean;
};
