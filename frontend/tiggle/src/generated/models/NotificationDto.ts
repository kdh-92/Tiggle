/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CommentRespDto } from './CommentRespDto';
import type { MemberResponseDto } from './MemberResponseDto';

export type NotificationDto = {
    id?: number;
    title?: string;
    content?: string;
    createdAt?: string;
    viewedAt?: string;
    sender?: MemberResponseDto;
    receiver?: MemberResponseDto;
    comment?: CommentRespDto;
};

