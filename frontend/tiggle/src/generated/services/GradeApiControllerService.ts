/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { GradeDto } from '../models/GradeDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class GradeApiControllerService {

    /**
     * @param id
     * @returns GradeDto OK
     * @throws ApiError
     */
    public static getGrade(
        id: number,
    ): CancelablePromise<GradeDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/grade/{id}',
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
     * @returns GradeDto OK
     * @throws ApiError
     */
    public static updateGrade(
        id: number,
        requestBody: GradeDto,
    ): CancelablePromise<GradeDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/grade/{id}',
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
     * @returns GradeDto OK
     * @throws ApiError
     */
    public static createGrade(
        requestBody: GradeDto,
    ): CancelablePromise<GradeDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/grade',
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
     * @returns GradeDto OK
     * @throws ApiError
     */
    public static getAllGrade(): CancelablePromise<Array<GradeDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/grade/all',
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

}
