/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

import type { ApiResponse } from "../models/ApiResponse";

export class ItemApiControllerService {
  /**
   * 내 보유 아이템 목록
   * @returns ApiResponse 보유 아이템 목록
   * @throws ApiError
   */
  public static getInventory(): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/items/inventory",
    });
  }

  /**
   * 전체 아이템 카탈로그 (잠금 상태 포함)
   * @returns ApiResponse 아이템 카탈로그
   * @throws ApiError
   */
  public static getCatalog(): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/items/catalog",
    });
  }

  /**
   * 내 장착 상태
   * @returns ApiResponse 장착 정보
   * @throws ApiError
   */
  public static getMyEquipment(): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/items/equipment",
    });
  }

  /**
   * 다른 유저 장착 상태
   * @param memberId 유저 ID
   * @returns ApiResponse 장착 정보
   * @throws ApiError
   */
  public static getEquipment(
    memberId: number,
  ): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/items/equipment/{memberId}",
      path: {
        memberId: memberId,
      },
    });
  }

  /**
   * 아이템 장착/해제
   * @param requestBody 장착 요청
   * @returns ApiResponse OK
   * @throws ApiError
   */
  public static equipItem(requestBody: {
    slot: string;
    itemId: number | null;
  }): CancelablePromise<ApiResponse> {
    return __request(OpenAPI, {
      method: "PUT",
      url: "/api/v1/items/equip",
      body: requestBody,
      mediaType: "application/json",
    });
  }
}
