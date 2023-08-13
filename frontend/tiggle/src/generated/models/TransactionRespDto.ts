/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { TransactionDto } from './TransactionDto';

export type TransactionRespDto = {
    memberId: number;
    parentId?: number;
    type?: 'INCOME' | 'OUTCOME' | 'REFUND';
    imageUrl: string;
    amount?: number;
    date?: string;
    content?: string;
    reason?: string;
    id?: number;
    parentTx?: TransactionDto;
    createdAt?: string;
};

