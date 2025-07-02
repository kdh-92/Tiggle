/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TagCreateReqDto } from '../models/TagCreateReqDto';
import type { TagRespDto } from '../models/TagRespDto';
import type { TagUpdateReqDto } from '../models/TagUpdateReqDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class TagApiControllerService {

    /**
     * @param id 
     * @returns TagRespDto OK
     * @throws ApiError
     */
    public static getTag(
id: number,
): CancelablePromise<TagRespDto> {
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
     * @returns TagRespDto OK
     * @throws ApiError
     */
    public static updateTag(
id: number,
requestBody: TagUpdateReqDto,
): CancelablePromise<TagRespDto> {
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
     * @returns TagRespDto OK
     * @throws ApiError
     */
    public static createTag(
requestBody: TagCreateReqDto,
): CancelablePromise<TagRespDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/tag',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns TagRespDto OK
     * @throws ApiError
     */
    public static getAllDefaultTag(): CancelablePromise<Array<TagRespDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/tag/all',
        });
    }

}
