/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ReactionCreateReqDto } from '../models/ReactionCreateReqDto';
import type { ReactionRespDto } from '../models/ReactionRespDto';
import type { ReactionSummaryRespDto } from '../models/ReactionSummaryRespDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ReactionApiService {

    /**
     * 해당 tx에 대한 나의 reaction을 조회
     * @param id 
     * @returns ReactionRespDto OK
     * @throws ApiError
     */
    public static getReaction(
id: number,
): CancelablePromise<ReactionRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/{id}/reaction',
            path: {
                'id': id,
            },
        });
    }

    /**
     * 리액션을 추가하거나 수정함
     * @param id 
     * @param requestBody 
     * @returns ReactionRespDto OK
     * @throws ApiError
     */
    public static upsertReaction(
id: number,
requestBody: ReactionCreateReqDto,
): CancelablePromise<ReactionRespDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/transaction/{id}/reaction',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
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
        });
    }

    /**
     * 해당 tx의 전체 reaction과 comment의 수를 조회
     * @param id 
     * @returns ReactionSummaryRespDto OK
     * @throws ApiError
     */
    public static getReactionSummary(
id: number,
): CancelablePromise<ReactionSummaryRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/{id}/reaction/summary',
            path: {
                'id': id,
            },
        });
    }

}
