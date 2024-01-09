/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';
import type { TransactionRespDto } from './TransactionRespDto';

export type PageTransactionRespDto = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: Array<TransactionRespDto>;
    number?: number;
    sort?: SortObject;
    pageable?: PageableObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    empty?: boolean;
};

