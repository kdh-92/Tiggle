/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiResponse } from '../models/ApiResponse';
import type { ApiResponseMemberListRespDto } from '../models/ApiResponseMemberListRespDto';
import type { ApiResponseMemberRespDto } from '../models/ApiResponseMemberRespDto';
import type { MemberCreateReqDto } from '../models/MemberCreateReqDto';
import type { MemberUpdateReqDto } from '../models/MemberUpdateReqDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class MemberApiControllerService {

    /**
     * 내 정보 조회
     * @returns ApiResponseMemberRespDto OK
     * @throws ApiError
     */
    public static getMe(): CancelablePromise<ApiResponseMemberRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/member/me',
        });
    }

    /**
     * 프로필 업데이트
     * @param formData 
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static updateMe(
formData?: {
dto?: MemberUpdateReqDto;
multipartFile?: Blob;
},
): CancelablePromise<ApiResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/member/me',
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }

    /**
     * @param requestBody 
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static createMember(
requestBody: MemberCreateReqDto,
): CancelablePromise<ApiResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/member',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param id 
     * @returns ApiResponseMemberRespDto OK
     * @throws ApiError
     */
    public static getMember(
id: number,
): CancelablePromise<ApiResponseMemberRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/member/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * @returns ApiResponseMemberListRespDto OK
     * @throws ApiError
     */
    public static getAllMember(): CancelablePromise<ApiResponseMemberListRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/member/all',
        });
    }

}
