/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CommentChildRespDto } from './CommentChildRespDto';

export type CommentPageRespDto = {
    comments: Array<CommentChildRespDto>;
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    isLast: boolean;
};
