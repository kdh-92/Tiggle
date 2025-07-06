/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiResponse } from '../models/ApiResponse';
import type { ApiResponseCommentPageRespDto } from '../models/ApiResponseCommentPageRespDto';
import type { CommentCreateReqDto } from '../models/CommentCreateReqDto';
import type { CommentUpdateReqDto } from '../models/CommentUpdateReqDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class CommentApiService {

    /**
     * 코멘트 수정
     * @param id 
     * @param requestBody 
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static updateComment(
id: number,
requestBody: CommentUpdateReqDto,
): CancelablePromise<ApiResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/comments/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * 코멘트 삭제
     * @param id 
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static deleteComment(
id: number,
): CancelablePromise<ApiResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/comments/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * 코멘트 작성
     * @param requestBody 
     * @returns ApiResponse OK
     * @throws ApiError
     */
    public static createComment(
requestBody: CommentCreateReqDto,
): CancelablePromise<ApiResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/comments',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * 대댓글 조회 API
     * 댓글의 id를 가지고 대댓글을 조회한다
     * @param id 
     * @param index 
     * @param pageSize 
     * @returns ApiResponseCommentPageRespDto OK
     * @throws ApiError
     */
    public static getAllCommentsByCommentId(
id: number,
index?: number,
pageSize: number = 5,
): CancelablePromise<ApiResponseCommentPageRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/comments/{id}/replies',
            path: {
                'id': id,
            },
            query: {
                'index': index,
                'pageSize': pageSize,
            },
        });
    }

    /**
     * @param id 
     * @param index 
     * @param pageSize 
     * @returns ApiResponseCommentPageRespDto OK
     * @throws ApiError
     */
    public static getAllCommentsByTx1(
id: number,
index?: number,
pageSize: number = 5,
): CancelablePromise<ApiResponseCommentPageRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/comments/{id}/comments',
            path: {
                'id': id,
            },
            query: {
                'index': index,
                'pageSize': pageSize,
            },
        });
    }

}
