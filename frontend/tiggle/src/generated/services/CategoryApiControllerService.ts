/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiResponse } from '../models/ApiResponse';
import type { ApiResponseCategoryListRespDto } from '../models/ApiResponseCategoryListRespDto';
import type { CategoryCreateReqDto } from '../models/CategoryCreateReqDto';
import type { CategoryUpdateReqDto } from '../models/CategoryUpdateReqDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class CategoryApiControllerService {

    /**
     * @param id 
     * @param requestBody 
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static updateCategory(
id: number,
requestBody: CategoryUpdateReqDto,
): CancelablePromise<ApiResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/category/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param id 
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static deleteCategory(
id: number,
): CancelablePromise<ApiResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/category/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * @param xMemberId 
     * @returns ApiResponseCategoryListRespDto OK
     * @throws ApiError
     */
    public static getCategoryByMemberIdOrDefaults(
xMemberId: number,
): CancelablePromise<ApiResponseCategoryListRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/category',
            headers: {
                'x-member-id': xMemberId,
            },
        });
    }

    /**
     * @param xMemberId 
     * @param requestBody 
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static createCategory(
xMemberId: number,
requestBody: CategoryCreateReqDto,
): CancelablePromise<ApiResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/category',
            headers: {
                'x-member-id': xMemberId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

}
