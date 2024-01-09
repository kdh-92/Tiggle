import React, { PropsWithChildren, cloneElement } from "react";
import { CookiesProvider } from "react-cookie";
import { Provider } from "react-redux";
import { BrowserRouter, RouterProvider } from "react-router-dom";

import { QueryClientProvider } from "@tanstack/react-query";
import { render } from "@testing-library/react";
import { ConfigProvider } from "antd";
import { PersistGate } from "redux-persist/integration/react";
import { ThemeProvider } from "styled-components";

import router from "@/Router";
import MessageProvider from "@/hooks/useMessage/MessageProvider";
import queryClient from "@/query/queryClient";
import { persistedStore, store } from "@/store";
import { GlobalStyle } from "@/styles/config/GlobalStyle";
import { antTheme } from "@/styles/config/antTheme";
import { mq } from "@/styles/config/mediaQueries";
import { theme } from "@/styles/config/theme";

interface MultiProvidersProps extends PropsWithChildren {
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

type RenderParams = Parameters<typeof render>;

export const renderWithProvider = (
  ui: RenderParams[0],
  options: RenderParams[1],
) => {
  render(
    <Provider store={store}>
      <MultiProviders
        providers={[
          <QueryClientProvider client={queryClient} />,
          <CookiesProvider />,
          <PersistGate persistor={persistedStore} />,
          <ThemeProvider theme={{ ...theme, mq }} />,
          <ConfigProvider theme={antTheme} />,
          <MessageProvider />,
          <BrowserRouter />,
        ]}
      >
        <GlobalStyle />
        {ui}
      </MultiProviders>
    </Provider>,
    {
      ...options,
    },
  );
};

// export const renderApp = (initialEntry: string = "/") => {
//   render(
//     <Provider store={store}>
//       <MultiProviders
//         providers={[
//           <QueryClientProvider client={queryClient} />,
//           <CookiesProvider />,
//           <PersistGate persistor={persistedStore} />,
//           <ThemeProvider theme={{ ...theme, mq }} />,
//           <ConfigProvider theme={antTheme} />,
//           <MessageProvider />,
//         ]}
//       >
//         <RouterProvider router={router} />
//       </MultiProviders>
//     </Provider>,
//   );
//   console.log(initialEntry);
// };
