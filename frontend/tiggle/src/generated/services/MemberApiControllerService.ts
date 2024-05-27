/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MemberRequestDto } from '../models/MemberRequestDto';
import type { MemberResponseDto } from '../models/MemberResponseDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class MemberApiControllerService {

    /**
     * 내 정보 조회
     * @returns MemberResponseDto OK
     * @throws ApiError
     */
    public static getMe(): CancelablePromise<MemberResponseDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/member/me',
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

    /**
     * 프로필 업데이트
     * @param formData
     * @returns MemberResponseDto OK
     * @throws ApiError
     */
    public static updateMe(
        formData?: {
            memberRequestDto: MemberRequestDto;
            multipartFile: Blob;
        },
    ): CancelablePromise<MemberResponseDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/member/me',
            formData: formData,
            mediaType: 'multipart/form-data',
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
     * @returns MemberResponseDto OK
     * @throws ApiError
     */
    public static createMember(
        requestBody: MemberRequestDto,
    ): CancelablePromise<MemberResponseDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/member',
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
     * @returns MemberResponseDto OK
     * @throws ApiError
     */
    public static getMember(
        id: number,
    ): CancelablePromise<MemberResponseDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/member/{id}',
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
     * @returns MemberResponseDto OK
     * @throws ApiError
     */
    public static getAllMember(): CancelablePromise<Array<MemberResponseDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/member/all',
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

}
