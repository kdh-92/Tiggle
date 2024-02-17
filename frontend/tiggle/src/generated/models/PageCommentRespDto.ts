/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CommentRespDto } from './CommentRespDto';
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';

export type PageCommentRespDto = {
    totalPages?: number;
    totalElements?: number;
    size?: number;
    content?: Array<CommentRespDto>;
    number?: number;
    sort?: SortObject;
    pageable?: PageableObject;
    numberOfElements?: number;
    first?: boolean;
    last?: boolean;
    empty?: boolean;
};

