/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type TransactionCreateReqDto = {
    categoryId: number;
    type: 'INCOME' | 'OUTCOME' | 'REFUND';
    imageUrl?: string;
    amount: number;
    date: string;
    content: string;
    reason: string;
    tagNames?: Array<string>;
};
