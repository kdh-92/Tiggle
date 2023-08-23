/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageCommentRespDto } from '../models/PageCommentRespDto';
import type { TransactionDto } from '../models/TransactionDto';
import type { TransactionRespDto } from '../models/TransactionRespDto';
import type { TransactionUpdateReqDto } from '../models/TransactionUpdateReqDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class TransactionApiControllerService {

    /**
     * tx 상세 조회
     * tx의 id에 대한 상세 정보를 반환합니다.
     * @param id tx의 id
     * @returns TransactionRespDto tx 상세 조회 성공
     * @throws ApiError
     */
    public static getTransaction(
        id: number,
    ): CancelablePromise<TransactionRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/{id}',
            path: {
                'id': id,
            },
            errors: {
                400: `존재하지 않는 리소스 접근`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

    /**
     * @param xMemberId
     * @param id
     * @param requestBody
     * @returns TransactionRespDto OK
     * @throws ApiError
     */
    public static updateTransaction(
        xMemberId: number,
        id: number,
        requestBody: TransactionUpdateReqDto,
    ): CancelablePromise<TransactionRespDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/transaction/{id}',
            path: {
                'id': id,
            },
            headers: {
                'x-member-id': xMemberId,
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
     * @returns any OK
     * @throws ApiError
     */
    public static deleteTransaction(
        xMemberId: number,
        id: number,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/transaction/{id}',
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
     * tx 페이지 조회 API
     * 페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.
     * @param index tx 페이지 번호
     * @param pageSize 페이지 내부 tx 개수
     * @returns TransactionRespDto tx 페이지 조회 성공
     * @throws ApiError
     */
    public static getCountOffsetTransaction(
        index: number,
        pageSize: number = 5,
    ): CancelablePromise<TransactionRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction',
            query: {
                'index': index,
                'pageSize': pageSize,
            },
            errors: {
                400: `존재하지 않는 리소스 접근`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

    /**
     * @param formData
     * @returns TransactionRespDto OK
     * @throws ApiError
     */
    public static createTransaction(
        formData?: {
            dto: TransactionDto;
            multipartFile: Blob;
        },
    ): CancelablePromise<TransactionRespDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/transaction',
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
     * @param pageSize
     * @param index
     * @returns PageCommentRespDto OK
     * @throws ApiError
     */
    public static getAllCommentsByTx(
        id: number,
        pageSize: number = 5,
        index?: number,
    ): CancelablePromise<PageCommentRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/{id}/comments',
            path: {
                'id': id,
            },
            query: {
                'pageSize': pageSize,
                'index': index,
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
     * 특정 유저 tx 페이지 조회 API
     * memberId 유저의 페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.
     * @param memberId 유저 id
     * @param index tx 페이지 번호
     * @param pageSize 페이지 내부 tx 개수
     * @returns TransactionRespDto tx 페이지 조회 성공
     * @throws ApiError
     */
    public static getMemberCountOffsetTransaction(
        memberId: number,
        index: number,
        pageSize: number = 5,
    ): CancelablePromise<TransactionRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/member',
            query: {
                'memberId': memberId,
                'index': index,
                'pageSize': pageSize,
            },
            errors: {
                400: `존재하지 않는 리소스 접근`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

    /**
     * @returns TransactionRespDto OK
     * @throws ApiError
     */
    public static getAllTransaction(): CancelablePromise<Array<TransactionRespDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/all',
            errors: {
                400: `Bad Request`,
                401: `Unauthorized`,
                404: `Not Found`,
                500: `Internal Server Error`,
            },
        });
    }

}
