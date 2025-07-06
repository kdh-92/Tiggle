/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiResponse } from '../models/ApiResponse';
import type { ApiResponseReactionSummaryRespDto } from '../models/ApiResponseReactionSummaryRespDto';
import type { ReactionCreateReqDto } from '../models/ReactionCreateReqDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ReactionApiService {

    /**
     * 해당 tx에 대한 나의 reaction을 조회
     * @param id 
     * @returns any OK
     * @throws ApiError
     */
    public static getReaction(
id: number,
): CancelablePromise<Record<string, any>> {
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
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static upsertReaction(
id: number,
requestBody: ReactionCreateReqDto,
): CancelablePromise<ApiResponse> {
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
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static deleteReaction(
id: number,
): CancelablePromise<ApiResponse> {
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
     * @returns ApiResponseReactionSummaryRespDto OK
     * @throws ApiError
     */
    public static getReactionSummary(
id: number,
): CancelablePromise<ApiResponseReactionSummaryRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/{id}/reaction/summary',
            path: {
                'id': id,
            },
        });
    }

}
