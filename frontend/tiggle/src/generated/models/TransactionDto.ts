/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type TransactionDto = {
    memberId: number;
    parentId?: number;
    type?: 'INCOME' | 'OUTCOME' | 'REFUND';
    imageUrl?: string;
    amount?: number;
    date?: string;
    content?: string;
    reason?: string;
    assetId?: number;
    categoryId?: number;
    tagNames?: string;
};

