import axios, { AxiosHeaders } from "axios";

import { CancelablePromise, OpenAPIConfig } from "@/generated";
import type { ApiRequestOptions } from "@/generated/core/ApiRequestOptions";
import useCookie from "@/hooks/useCookie";

export const getAxiosInstance = () => {
  const { getCookie, setCookie, removeCookie } = useCookie();
  const token = getCookie("Authorization");

  const axiosInstance = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      ...(token && { Authorization: `Bearer ${token}` }),
    },
  });

  axiosInstance.interceptors.response.use(
    response => response,
    async error => {
      const originalRequest = error.config;

      if (error.response?.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;
        const refreshToken = getCookie("RefreshToken");

        if (refreshToken) {
          try {
            const refreshResponse = await axios.post(
              `${import.meta.env.VITE_API_URL}api/auth/refresh`,
              null,
              {
                headers: {
                  "Refresh-Token": refreshToken,
                  "Content-Type": "application/json",
                },
              },
            );

            const { data } = refreshResponse.data;
            const { accessToken, refreshToken: newRefreshToken } = data;

            setCookie("Authorization", accessToken, {
              path: "/",
              maxAge: 60 * 60 * 24,
            });
            setCookie("RefreshToken", newRefreshToken, {
              path: "/",
              maxAge: 60 * 60 * 24 * 7,
            });

            originalRequest.headers.Authorization = `Bearer ${accessToken}`;
            return axios.request(originalRequest);
          } catch (refreshError) {
            console.error("Token refresh failed:", refreshError);

            removeCookie("Authorization");
            removeCookie("RefreshToken");

            if (window.location.pathname !== "/login") {
              window.location.href = "/login";
            }

            return Promise.reject(refreshError);
          }
        } else {
          removeCookie("Authorization");

          if (window.location.pathname !== "/login") {
            window.location.href = "/login";
          }
        }
      }

      return Promise.reject(error);
    },
  );

  return axiosInstance;
};

export const request = <T>(
  config: OpenAPIConfig,
  options: ApiRequestOptions,
): CancelablePromise<T> =>
  new CancelablePromise((resolve, reject) => {
    const urlWithPath = getUrlWithPath(options.url, options.path);
    const queryString = getQueryString(options.query);
    const url = `${urlWithPath}${queryString}`;

    getAxiosInstance()
      .request({
        url,
        data: options.body,
        method: options.method,
        headers: options.headers as AxiosHeaders,
      })
      .then(({ data }) => resolve(data))
      .catch((error: any) => reject(error));
  });

export const getQueryString = (params?: Record<string, any>): string => {
  if (!params) {
    return "";
  }

  const qs: string[] = [];
  const append = (key: string, value: any) => {
    qs.push(`${encodeURIComponent(key)}=${encodeURIComponent(String(value))}`);
  };

  const process = (key: string, value: any) => {
    if (value === undefined || value === null) {
      return;
    }
    if (Array.isArray(value)) {
      value.forEach(v => {
        process(key, v);
      });
    } else if (typeof value === "object") {
      Object.entries(value).forEach(([k, v]) => {
        process(k, v);
      });
    } else {
      append(key, value);
    }
  };

  Object.entries(params).forEach(([key, value]) => {
    process(key, value);
  });

  return qs.length > 0 ? `?${qs.join("&")}` : "";
};

const getUrlWithPath = (url: string, paths?: Record<string, string>) =>
  url.replace(/{(.*?)}/g, (substring, group) => {
    if (paths?.hasOwnProperty(group)) {
      return String(paths[group]);
    }
    return substring;
  });
