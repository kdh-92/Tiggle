/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MemberCreateReqDto } from '../models/MemberCreateReqDto';
import type { MemberListRespDto } from '../models/MemberListRespDto';
import type { MemberRespDto } from '../models/MemberRespDto';
import type { MemberUpdateReqDto } from '../models/MemberUpdateReqDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class MemberApiControllerService {

    /**
     * 내 정보 조회
     * @returns MemberRespDto OK
     * @throws ApiError
     */
    public static getMe(): CancelablePromise<MemberRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/member/me',
        });
    }

    /**
     * 프로필 업데이트
     * @param formData 
     * @returns MemberRespDto OK
     * @throws ApiError
     */
    public static updateMe(
formData?: {
dto?: MemberUpdateReqDto;
multipartFile?: Blob;
},
): CancelablePromise<MemberRespDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/member/me',
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }

    /**
     * @param requestBody 
     * @returns MemberRespDto OK
     * @throws ApiError
     */
    public static createMember(
requestBody: MemberCreateReqDto,
): CancelablePromise<MemberRespDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/member',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param id 
     * @returns MemberRespDto OK
     * @throws ApiError
     */
    public static getMember(
id: number,
): CancelablePromise<MemberRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/member/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * @returns MemberListRespDto OK
     * @throws ApiError
     */
    public static getAllMember(): CancelablePromise<MemberListRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/member/all',
        });
    }

}
