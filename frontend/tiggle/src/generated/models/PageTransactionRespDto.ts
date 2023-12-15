/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';
import type { TransactionRespDto } from './TransactionRespDto';

export type PageTransactionRespDto = {
    totalPages?: number;
    totalElements?: number;
    size?: number;
    content?: Array<TransactionRespDto>;
    number?: number;
    sort?: SortObject;
    pageable?: PageableObject;
    numberOfElements?: number;
    first?: boolean;
    last?: boolean;
    empty?: boolean;
};

