/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssetDto } from '../models/AssetDto';
import type { AssetRespDto } from '../models/AssetRespDto';
import type { AssetUpdateReqDto } from '../models/AssetUpdateReqDto';
import type { Unit } from '../models/Unit';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AssetApiControllerService {

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
     * @param id
     * @param requestBody
     * @returns AssetRespDto OK
     * @throws ApiError
     */
    public static updateAsset(
        id: number,
        requestBody: AssetUpdateReqDto,
    ): CancelablePromise<AssetRespDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/asset/{id}',
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
     * @param xMemberId
     * @param id
     * @returns Unit OK
     * @throws ApiError
     */
    public static deleteAsset(
        xMemberId: number,
        id: number,
    ): CancelablePromise<Unit> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/asset/{id}',
            path: {
                'id': id,
            },
            headers: {
                'x-member-id': xMemberId,
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
     * @param requestBody
     * @returns AssetRespDto OK
     * @throws ApiError
     */
    public static createAsset(
        requestBody: AssetDto,
    ): CancelablePromise<AssetRespDto> {
        return __request(OpenAPI, {
            method: 'POST',
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
