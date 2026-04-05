/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CategoryRespDto } from './CategoryRespDto';
import type { MemberRespDto } from './MemberRespDto';

export type TransactionRespDto = {
    id: number;
    member: MemberRespDto;
    category: CategoryRespDto;
    tagNames?: Array<string>;
    createdAt: string;
    parentId?: number;
    imageUrls?: string;
    amount: number;
    date: string;
    content: string;
    reason: string;
};
