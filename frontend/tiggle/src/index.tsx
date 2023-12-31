import { cloneElement } from "react";
import { CookiesProvider } from "react-cookie";
import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import { RouterProvider } from "react-router-dom";

import { QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { ConfigProvider } from "antd";
import { PersistGate } from "redux-persist/integration/react";
import { ThemeProvider } from "styled-components";

import router from "@/Router";
import queryClient from "@/query/queryClient";
import { store, persistedStore } from "@/store";
import { GlobalStyle } from "@/styles/config/GlobalStyle";
import { antTheme } from "@/styles/config/antTheme";
import { mq } from "@/styles/config/mediaQueries";
import { theme } from "@/styles/config/theme";

const container = document.getElementById("root");
const root = createRoot(container);

interface MultiProvidersProps extends React.PropsWithChildren {
  providers: Array<React.ReactElement>;
}

const MultiProviders = ({ providers, children }: MultiProvidersProps) => (
  <>
    {providers.reduceRight(
      (accumulator: React.ReactNode, provider: React.ReactElement) =>
        cloneElement(provider, {}, accumulator),
      children,
    )}
  </>
);

root.render(
  <Provider store={store}>
    <MultiProviders
      providers={[
        <QueryClientProvider client={queryClient} />,
        <CookiesProvider />,
        <PersistGate persistor={persistedStore} />,
        <ThemeProvider theme={{ ...theme, mq }} />,
        <ConfigProvider theme={antTheme} />,
      ]}
    >
      {process.env.NODE_ENV === "development" && <ReactQueryDevtools />}
      <GlobalStyle />
      <RouterProvider router={router} />
    </MultiProviders>
  </Provider>,
);
