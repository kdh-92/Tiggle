/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type TransactionUpdateReqDto = {
    type?: 'INCOME' | 'OUTCOME' | 'REFUND';
    amount?: number;
    date?: string;
    content?: string;
    reason?: string;
    assetId?: number;
    categoryId?: number;
    tagNames?: string;
};

