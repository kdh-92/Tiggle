import "@testing-library/jest-dom";
import { expect, afterEach, vi, beforeAll, afterAll } from 'vitest';
import { cleanup } from '@testing-library/react';
import * as matchers from "@testing-library/jest-dom/matchers";
import server from "./tests/__mocks__/server";

// react-testing-library의 matcher를 확장한다. 
// `@testing-library/jest-dom`의 matcher를 사용할 수 있게 된다.
expect.extend(matchers);

beforeAll(() => server.listen())
afterAll(() => server.close())

afterEach(() => {
  // Remove any handlers you may have added
  // in individual tests (runtime handlers).
  server.resetHandlers()
  // test 간 DOM 상태를 초기화
  cleanup();
})

// ANTD error 해결
Object.defineProperty(window, 'matchMedia', {
  writable: true,
  value: vi.fn().mockImplementation((query) => ({
    matches: false,
    media: query,
    onchange: null,
    addListener: vi.fn(), // deprecated
    removeListener: vi.fn(), // deprecated
    addEventListener: vi.fn(),
    removeEventListener: vi.fn(),
    dispatchEvent: vi.fn(),
  })),
});
