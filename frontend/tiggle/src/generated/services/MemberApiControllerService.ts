/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MemberDto } from '../models/MemberDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class MemberApiControllerService {

    /**
     * 내 정보 조회
     * @returns MemberDto OK
     * @throws ApiError
     */
    public static getMe(): CancelablePromise<MemberDto> {
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
     * @returns MemberDto OK
     * @throws ApiError
     */
    public static updateMe(
        formData?: {
            memberDto: MemberDto;
            multipartFile?: Blob;
        },
    ): CancelablePromise<MemberDto> {
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
     * @param id
     * @returns MemberDto OK
     * @throws ApiError
     */
    public static getMember(
        id: number,
    ): CancelablePromise<MemberDto> {
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
     * @returns MemberDto OK
     * @throws ApiError
     */
    public static getAllMember(): CancelablePromise<Array<MemberDto>> {
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
