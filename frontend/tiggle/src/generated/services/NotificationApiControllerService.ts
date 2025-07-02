/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { NotificationRespDto } from '../models/NotificationRespDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class NotificationApiControllerService {

    /**
     * @param id 
     * @returns string OK
     * @throws ApiError
     */
    public static readNotification(
id: number,
): CancelablePromise<string> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/notification/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * 모든 알림 조회
     * @param memberId 
     * @returns NotificationRespDto OK
     * @throws ApiError
     */
    public static getAllByMember(
memberId?: number,
): CancelablePromise<Array<NotificationRespDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/notification',
            headers: {
                'member-id': memberId,
            },
        });
    }

}
