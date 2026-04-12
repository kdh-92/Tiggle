/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

import type { ApiResponse } from "../models/ApiResponse";

export class CharacterApiControllerService {
  /**
   * 내 캐릭터 조회
   * @returns ApiResponse 캐릭터 상세 정보
   * @throws ApiError
   */
  public static getMyCharacter(): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/character/me",
    });
  }

  /**
   * 다른 유저 캐릭터 조회
   * @param memberId 유저 ID
   * @returns ApiResponse 캐릭터 상세 정보
   * @throws ApiError
   */
  public static getCharacter(memberId: number): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/character/{memberId}",
      path: {
        memberId: memberId,
      },
    });
  }

  /**
   * 캐릭터 카탈로그 (전체 도감)
   * @returns ApiResponse 캐릭터 카탈로그 목록
   * @throws ApiError
   */
  public static getCatalog(): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/character/catalog",
    });
  }
}
