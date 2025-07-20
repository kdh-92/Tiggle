/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiResponseMapStringObject } from '../models/ApiResponseMapStringObject';
import type { ApiResponseTokenResponse } from '../models/ApiResponseTokenResponse';
import type { ApiResponseUnit } from '../models/ApiResponseUnit';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AuthControllerService {

    /**
     * 토큰 갱신
     * Refresh Token을 사용하여 새로운 Access Token을 발급받습니다
     * @returns ApiResponseTokenResponse OK
     * @throws ApiError
     */
    public static refreshToken(): CancelablePromise<ApiResponseTokenResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/auth/refresh',
        });
    }

    /**
     * 로그아웃
     * 현재 사용자의 Refresh Token을 무효화합니다
     * @returns ApiResponseUnit OK
     * @throws ApiError
     */
    public static logout(): CancelablePromise<ApiResponseUnit> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/auth/logout',
        });
    }

    /**
     * 토큰 유효성 검증
     * 현재 Access Token의 유효성을 확인합니다
     * @returns ApiResponseMapStringObject OK
     * @throws ApiError
     */
    public static validateToken(): CancelablePromise<ApiResponseMapStringObject> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/auth/validate',
        });
    }

}
