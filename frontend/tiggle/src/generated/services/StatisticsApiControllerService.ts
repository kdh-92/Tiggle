/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

import type { ApiResponse } from "../models/ApiResponse";

export class StatisticsApiControllerService {
  /**
   * 주간 소비 비교
   * @param weekOffset 주차 오프셋 (0=이번주, 1=지난주, ...)
   * @returns ApiResponse 주간 비교 데이터
   * @throws ApiError
   */
  public static getWeeklyComparison(
    weekOffset: number = 0,
  ): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/statistics/weekly-comparison",
      query: {
        weekOffset: weekOffset,
      },
    });
  }

  /**
   * 월간 소비 요약
   * @param year 년도
   * @param month 월
   * @returns ApiResponse 월간 요약 데이터
   * @throws ApiError
   */
  public static getMonthlySummary(
    year?: number,
    month?: number,
  ): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/statistics/monthly-summary",
      query: {
        year: year,
        month: month,
      },
    });
  }
}
