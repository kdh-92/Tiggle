/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CommentPageRespDto } from '../models/CommentPageRespDto';
import type { TransactionCreateReqDto } from '../models/TransactionCreateReqDto';
import type { TransactionListRespDto } from '../models/TransactionListRespDto';
import type { TransactionPageRespDto } from '../models/TransactionPageRespDto';
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
            },
        });
    }

    /**
     * 트랜잭션 수정
     * @param id 
     * @param requestBody 
     * @returns TransactionRespDto OK
     * @throws ApiError
     */
    public static updateTransaction(
id: number,
requestBody: TransactionUpdateReqDto,
): CancelablePromise<TransactionRespDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/transaction/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * 트랜잭션 삭제
     * @param id 
     * @returns string OK
     * @throws ApiError
     */
    public static deleteTransaction(
id: number,
): CancelablePromise<string> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/transaction/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * tx 페이지 조회 API
     * 페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.
     * @param index tx 페이지 번호
     * @param pageSize 페이지 내부 tx 개수
     * @returns TransactionPageRespDto tx 페이지 조회 성공
     * @throws ApiError
     */
    public static getCountOffsetTransaction(
index?: number,
pageSize: number = 5,
): CancelablePromise<TransactionPageRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction',
            query: {
                'index': index,
                'pageSize': pageSize,
            },
            errors: {
                400: `존재하지 않는 리소스 접근`,
            },
        });
    }

    /**
     * tx 생성
     * @param formData 
     * @returns TransactionRespDto OK
     * @throws ApiError
     */
    public static createTransaction(
formData?: {
dto: TransactionCreateReqDto;
multipartFile?: Blob;
},
): CancelablePromise<TransactionRespDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/transaction',
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }

    /**
     * @param id 
     * @param pageSize 
     * @param index 
     * @returns CommentPageRespDto OK
     * @throws ApiError
     */
    public static getAllCommentsByTx(
id: number,
pageSize: number = 5,
index?: number,
): CancelablePromise<CommentPageRespDto> {
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
        });
    }

    /**
     * 특정 유저 tx 페이지 조회 API
     * memberId 유저의 페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.
     * @param memberId 유저 id
     * @param index tx 페이지 번호
     * @param pageSize 페이지 내부 tx 개수
     * @param start (필터링) 트랜잭션 일자 기준 시작
     * @param end (필터링) 트랜잭션 일자 기준 끝
     * @param category (필터링) 카테고리 종류 (복수)
     * @param asset (필터링) 자산 종류 (복수)
     * @param tagNames (필터링) 태그 이름 (복수)
     * @returns TransactionPageRespDto tx 페이지 조회 성공
     * @throws ApiError
     */
    public static getMemberCountOffsetTransaction(
memberId: number,
index?: number,
pageSize: number = 5,
start?: string,
end?: string,
category?: Array<number>,
asset?: Array<number>,
tagNames?: Array<string>,
): CancelablePromise<TransactionPageRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/member',
            query: {
                'memberId': memberId,
                'index': index,
                'pageSize': pageSize,
                'start': start,
                'end': end,
                'category': category,
                'asset': asset,
                'tagNames': tagNames,
            },
            errors: {
                400: `존재하지 않는 리소스 접근`,
            },
        });
    }

    /**
     * @returns TransactionListRespDto OK
     * @throws ApiError
     */
    public static getAllTransaction(): CancelablePromise<TransactionListRespDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/transaction/all',
        });
    }

}
