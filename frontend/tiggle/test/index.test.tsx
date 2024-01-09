import React from "react";

import { screen } from "@testing-library/react";
import "@testing-library/jest-dom";

// import LoginPage from "@/pages/LoginPage";
import LoginHeader from "@/pages/LoginPage/LoginHeader/LoginHeader";
import MainPage from "@/pages/MainPage";

import { renderWithProvider } from "./util/render";

test("test", () => {
  const a = 1;
  expect(a).toBeGreaterThanOrEqual(1);
});

const TestComponent = () => <div color="blue">hello</div>;
test("test2", async () => {
  renderWithProvider(<TestComponent />, {});

  const text = await screen.findByText("hello");
  expect(text).toHaveAttribute("color", "blue");
});

test("test3", async () => {
  // renderApp();
  renderWithProvider(<LoginHeader />, {});

  expect(1).toBe(1);
});
