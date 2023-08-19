import axios from "axios";

import { CancelablePromise, OpenAPIConfig } from "@/generated";
import type { ApiRequestOptions } from "@/generated/core/ApiRequestOptions";

const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

export const request = <T>(
  config: OpenAPIConfig,
  options: ApiRequestOptions,
): CancelablePromise<T> =>
  new CancelablePromise((resolve, reject) => {
    const urlWithPath = getUrlWithPath(options.url, options.path);
    const queryString = getQueryString(options.query);
    const url = `${urlWithPath}${queryString}`;

    axiosInstance
      .request({
        url,
        data: options.body,
        method: options.method,
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
