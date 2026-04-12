/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

import type { ApiResponse } from "../models/ApiResponse";

export class AchievementApiControllerService {
  /**
   * 전체 업적 목록 (달성 여부 포함)
   * @returns ApiResponse 업적 목록
   * @throws ApiError
   */
  public static getAchievements(): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/achievements",
    });
  }

  /**
   * 최근 달성 업적
   * @param limit 조회 개수
   * @returns ApiResponse 최근 업적 목록
   * @throws ApiError
   */
  public static getRecentAchievements(
    limit: number = 5,
  ): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/achievements/recent",
      query: {
        limit: limit,
      },
    });
  }
}
