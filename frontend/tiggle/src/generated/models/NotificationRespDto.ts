/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CommentRespDto } from './CommentRespDto';
import type { MemberRespDto } from './MemberRespDto';

export type NotificationRespDto = {
    id: number;
    title?: string;
    content?: string;
    createdAt: string;
    viewedAt?: string;
    sender?: MemberRespDto;
    receiver?: MemberRespDto;
    comment?: CommentRespDto;
};
