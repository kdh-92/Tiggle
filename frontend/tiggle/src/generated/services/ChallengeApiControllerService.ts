/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

import type { ApiResponse } from "../models/ApiResponse";

export class ChallengeApiControllerService {
  /**
   * 챌린지 생성
   * @param requestBody 챌린지 생성 요청
   * @returns ApiResponse 생성된 챌린지
   * @throws ApiError
   */
  public static createChallenge(requestBody: {
    type: string;
    targetDays: number;
  }): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/v1/challenges",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * 진행 중 챌린지 조회
   * @returns ApiResponse 현재 활성 챌린지
   * @throws ApiError
   */
  public static getActiveChallenge(): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/challenges/active",
    });
  }

  /**
   * 챌린지 상세 (일별 로그 포함)
   * @param id 챌린지 ID
   * @returns ApiResponse 챌린지 상세 정보
   * @throws ApiError
   */
  public static getChallengeDetail(
    id: number,
  ): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/challenges/{id}",
      path: {
        id: id,
      },
    });
  }

  /**
   * 완료/실패 챌린지 목록
   * @param page 페이지 번호
   * @param size 페이지 크기
   * @returns ApiResponse 챌린지 히스토리
   * @throws ApiError
   */
  public static getChallengeHistory(
    page: number = 0,
    size: number = 10,
  ): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/challenges/history",
      query: {
        page: page,
        size: size,
      },
    });
  }

  /**
   * 챌린지 취소
   * @param id 챌린지 ID
   * @returns ApiResponse OK
   * @throws ApiError
   */
  public static cancelChallenge(id: number): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "DELETE",
      url: "/api/v1/challenges/{id}",
      path: {
        id: id,
      },
    });
  }
}
