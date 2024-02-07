/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ReactionCreateDto } from '../models/ReactionCreateDto';
import type { ReactionDto } from '../models/ReactionDto';
import type { ReactionSummaryDto } from '../models/ReactionSummaryDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ReactionApiService {

    /**
     * 해당 tx에 대한 나의 reaction을 조회
     * @param id
     * @returns ReactionDto OK
     * @throws ApiError
     */
    public static getReaction(
        id: number,
    ): CancelablePromise<ReactionDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/{id}/reaction',
            path: {
                'id': id,
            },
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

    /**
     * 리액션을 추가하거나 수정함
     * @param id
     * @param reactionDto
     * @returns ReactionDto OK
     * @throws ApiError
     */
    public static upsertReaction(
        id: number,
        reactionDto: ReactionCreateDto,
    ): CancelablePromise<ReactionDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/transaction/{id}/reaction',
            path: {
                'id': id,
            },
            query: {
                'reactionDto': reactionDto,
            },
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

    /**
     * Reaction을 제거
     * @param id
     * @returns number OK
     * @throws ApiError
     */
    public static deleteReaction(
        id: number,
    ): CancelablePromise<number> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/transaction/{id}/reaction',
            path: {
                'id': id,
            },
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

    /**
     * 해당 tx의 전체 reaction과 comment의 수를 조회
     * @param id
     * @returns ReactionSummaryDto OK
     * @throws ApiError
     */
    public static getReactionSummary(
        id: number,
    ): CancelablePromise<ReactionSummaryDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/{id}/reaction/summary',
            path: {
                'id': id,
            },
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

}
