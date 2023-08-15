/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TxTagDto } from '../models/TxTagDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class TxTagApiControllerService {

    /**
     * @param id
     * @returns TxTagDto OK
     * @throws ApiError
     */
    public static getTxTag(
        id: number,
    ): CancelablePromise<TxTagDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/txtag/{id}',
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
     * @returns TxTagDto OK
     * @throws ApiError
     */
    public static updateComment(
        id: number,
        requestBody: TxTagDto,
    ): CancelablePromise<TxTagDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/txtag/{id}',
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
     * @returns TxTagDto OK
     * @throws ApiError
     */
    public static createTxTag(
        requestBody: TxTagDto,
    ): CancelablePromise<TxTagDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/txtag',
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
     * @returns TxTagDto OK
     * @throws ApiError
     */
    public static getAllTxTag(): CancelablePromise<Array<TxTagDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/txtag/all',
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

}
