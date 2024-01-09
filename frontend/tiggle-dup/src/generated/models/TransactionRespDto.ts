/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { AssetDto } from './AssetDto';
import type { CategoryDto } from './CategoryDto';
import type { MemberDto } from './MemberDto';
import type { TransactionDto } from './TransactionDto';

export type TransactionRespDto = {
    parentId?: number;
    type?: 'INCOME' | 'OUTCOME' | 'REFUND';
    imageUrl?: string;
    amount?: number;
    date?: string;
    content?: string;
    reason?: string;
    id?: number;
    member?: MemberDto;
    parentTx?: TransactionDto;
    asset?: AssetDto;
    category?: CategoryDto;
    txTagNames?: string;
    txUpCount?: number;
    txDownCount?: number;
    txCommentCount?: number;
    createdAt?: string;
};

