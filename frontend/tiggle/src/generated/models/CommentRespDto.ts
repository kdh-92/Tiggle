/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MemberResponseDto } from './MemberResponseDto';

export type CommentRespDto = {
    txId?: number;
    parentId?: number;
    senderId?: number;
    receiverId?: number;
    content?: string;
    id?: number;
    createdAt?: string;
    childCount?: number;
    sender?: MemberResponseDto;
    receiver?: MemberResponseDto;
};

