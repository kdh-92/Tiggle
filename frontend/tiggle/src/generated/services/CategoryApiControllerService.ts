/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CategoryDto } from '../models/CategoryDto';
import type { CategoryRespDto } from '../models/CategoryRespDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class CategoryApiControllerService {

    /**
     * @param requestBody
     * @returns CategoryRespDto OK
     * @throws ApiError
     */
    public static createCategory(
        requestBody: CategoryDto,
    ): CancelablePromise<CategoryRespDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/category',
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
     * @param id
     * @returns CategoryRespDto OK
     * @throws ApiError
     */
    public static getCategory(
        id: number,
    ): CancelablePromise<CategoryRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/category/{id}',
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
     * @param type
     * @returns CategoryRespDto OK
     * @throws ApiError
     */
    public static getCategory1(
        type: 'INCOME' | 'OUTCOME',
    ): CancelablePromise<Array<CategoryRespDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/category/type/{type}',
            path: {
                'type': type,
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
     * @returns CategoryRespDto OK
     * @throws ApiError
     */
    public static getAllCategory(): CancelablePromise<Array<CategoryRespDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/category/all',
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

}
