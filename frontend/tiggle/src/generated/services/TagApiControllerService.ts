/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TagDto } from '../models/TagDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class TagApiControllerService {

    /**
     * @param id
     * @returns TagDto OK
     * @throws ApiError
     */
    public static getTag(
        id: number,
    ): CancelablePromise<TagDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/tag/{id}',
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
     * @param id
     * @param requestBody
     * @returns TagDto OK
     * @throws ApiError
     */
    public static updateTag(
        id: number,
        requestBody: TagDto,
    ): CancelablePromise<TagDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/tag/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

    /**
     * @param requestBody
     * @returns TagDto OK
     * @throws ApiError
     */
    public static createTag(
        requestBody: TagDto,
    ): CancelablePromise<TagDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/tag',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

    /**
     * @returns TagDto OK
     * @throws ApiError
     */
    public static getAllTag(): CancelablePromise<Array<TagDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/tag/all',
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

}
