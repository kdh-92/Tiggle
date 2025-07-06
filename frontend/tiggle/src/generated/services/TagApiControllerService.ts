/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiResponse } from '../models/ApiResponse';
import type { ApiResponseListTagRespDto } from '../models/ApiResponseListTagRespDto';
import type { ApiResponseTagRespDto } from '../models/ApiResponseTagRespDto';
import type { TagCreateReqDto } from '../models/TagCreateReqDto';
import type { TagUpdateReqDto } from '../models/TagUpdateReqDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class TagApiControllerService {

    /**
     * @param id 
     * @returns ApiResponseTagRespDto OK
     * @throws ApiError
     */
    public static getTag(
id: number,
): CancelablePromise<ApiResponseTagRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/tag/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * @param id 
     * @param requestBody 
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static updateTag(
id: number,
requestBody: TagUpdateReqDto,
): CancelablePromise<ApiResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/tag/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param requestBody 
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static createTag(
requestBody: TagCreateReqDto,
): CancelablePromise<ApiResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/tag',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns ApiResponseListTagRespDto OK
     * @throws ApiError
     */
    public static getAllDefaultTag(): CancelablePromise<ApiResponseListTagRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/tag/all',
        });
    }

}
