/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MemberInfo } from './MemberInfo';

export type CommentRespDto = {
    id: number;
    txId: number;
    parentId?: number;
    content: string;
    createdAt: string;
    sender: MemberInfo;
    receiver: MemberInfo;
};
