/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CategoryCreateReqDto } from "../models/CategoryCreateReqDto";
import type { CategoryListRespDto } from "../models/CategoryListRespDto";
import type { CategoryRespDto } from "../models/CategoryRespDto";
import type { CategoryUpdateReqDto } from "../models/CategoryUpdateReqDto";

import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class CategoryApiControllerService {
  /**
   * @param id
   * @param requestBody
   * @returns CategoryRespDto OK
   * @throws ApiError
   */
  public static updateCategory(
    id: number,
    requestBody: CategoryUpdateReqDto,
  ): CancelablePromise<CategoryRespDto> {
    return __request(OpenAPI, {
      method: "PUT",
      url: "/api/v1/category/{id}",
      path: {
        id: id,
      },
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param id
   * @returns any OK
   * @throws ApiError
   */
  public static deleteCategory(id: number): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: "DELETE",
      url: "/api/v1/category/{id}",
      path: {
        id: id,
      },
    });
  }

  /**
   * @param xMemberId
   * @returns CategoryListRespDto OK
   * @throws ApiError
   */
  public static getCategoryByMemberIdOrDefaults(
    xMemberId: number,
  ): CancelablePromise<CategoryListRespDto> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/v1/category",
      headers: {
        "x-member-id": xMemberId,
      },
    });
  }

  /**
   * @param xMemberId
   * @param requestBody
   * @returns CategoryRespDto OK
   * @throws ApiError
   */
  public static createCategory(
    xMemberId: number,
    requestBody: CategoryCreateReqDto,
  ): CancelablePromise<CategoryRespDto> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/v1/category",
      headers: {
        "x-member-id": xMemberId,
      },
      body: requestBody,
      mediaType: "application/json",
    });
  }
}
