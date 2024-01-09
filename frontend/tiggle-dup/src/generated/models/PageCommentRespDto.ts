/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CommentRespDto } from './CommentRespDto';
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';

export type PageCommentRespDto = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: Array<CommentRespDto>;
    number?: number;
    sort?: SortObject;
    pageable?: PageableObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    empty?: boolean;
};

