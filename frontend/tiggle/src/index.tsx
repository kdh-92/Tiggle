import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import { BrowserRouter } from "react-router-dom";

import { QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { ThemeProvider } from "styled-components";

import queryClient from "@/query/queryClient";
import { store } from "@/store";
import { GlobalStyle } from "@/styles/config/GlobalStyle";
import { mq } from "@/styles/config/mediaQueries";
import { theme } from "@/styles/config/theme";

import Router from "./Router";

const container = document.getElementById("root");
const root = createRoot(container);

root.render(
  <Provider store={store}>
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={{ ...theme, mq }}>
        {process.env.NODE_ENV === "development" && <ReactQueryDevtools />}
        <GlobalStyle />
        <BrowserRouter>
          <Router />
        </BrowserRouter>
      </ThemeProvider>
    </QueryClientProvider>
  </Provider>,
);
