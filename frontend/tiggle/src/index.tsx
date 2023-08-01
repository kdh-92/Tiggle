import { createRoot } from "react-dom/client";
import { ThemeProvider } from "styled-components";
import Router from "./Router";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { Provider } from "react-redux";
import { BrowserRouter } from "react-router-dom";
import { store } from "./store";
import { GlobalStyle } from "./styles/config/GlobalStyle";

import { theme } from "@/styles/config/theme";
import { mq } from "@/styles/config/mediaQueries";

const queryClient = new QueryClient();
const container = document.getElementById("root");
const root = createRoot(container);

root.render(
  <Provider store={store}>
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={{ ...theme, ...mq }}>
        {process.env.NODE_ENV === "development" && <ReactQueryDevtools />}
        <GlobalStyle />
        <BrowserRouter>
          <Router />
        </BrowserRouter>
      </ThemeProvider>
    </QueryClientProvider>
  </Provider>,
);
