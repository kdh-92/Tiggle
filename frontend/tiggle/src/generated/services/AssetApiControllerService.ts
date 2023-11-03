/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssetDto } from '../models/AssetDto';
import type { AssetRespDto } from '../models/AssetRespDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AssetApiControllerService {

    /**
     * @param requestBody
     * @returns AssetRespDto OK
     * @throws ApiError
     */
    public static createAsset(
        requestBody: AssetDto,
    ): CancelablePromise<AssetRespDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/asset',
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
     * @returns AssetRespDto OK
     * @throws ApiError
     */
    public static getAsset(
        id: number,
    ): CancelablePromise<AssetRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/asset/{id}',
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
     * @returns AssetRespDto OK
     * @throws ApiError
     */
    public static getAllAsset(): CancelablePromise<Array<AssetRespDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/asset/all',
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

}
