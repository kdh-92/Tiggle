/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MemberResponseDto } from './MemberResponseDto';
import type { TransactionDto } from './TransactionDto';

export type TransactionRespDto = {
    id?: number;
    member?: MemberResponseDto;
    // asset?: AssetRespDto;
    // category?: CategoryRespDto;
    // txTagNames?: string;
    createdAt?: string;
    parentId?: number;
    type?: 'INCOME' | 'OUTCOME' | 'REFUND';
    imageUrl?: string;
    amount?: number;
    date?: string;
    content?: string;
    reason?: string;
    parentTx?: TransactionDto;
    txUpCount?: number;
    txDownCount?: number;
    txCommentCount?: number;
};

