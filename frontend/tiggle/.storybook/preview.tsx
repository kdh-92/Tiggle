import type { Preview } from "@storybook/react";
import { Provider } from "react-redux";
import { BrowserRouter } from 'react-router-dom';
import { QueryClientProvider } from "@tanstack/react-query";
import { ThemeProvider } from "styled-components";
import { ConfigProvider } from "antd";

import { theme } from "@/styles/config/theme";
import { antTheme } from "@/styles/config/antTheme";
import { GlobalStyle } from "@/styles/config/GlobalStyle";
import { mq } from "@/styles/config/mediaQueries";
import { store } from "@/store";
import queryClient from "@/query/queryClient";

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: "^on[A-Z].*" },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
  },
  decorators: [
    Story => (
      <QueryClientProvider client={queryClient}>
        <Provider store={store}>
          <ThemeProvider theme={{ ...theme, mq }}>
            <ConfigProvider theme={antTheme}>
              <GlobalStyle />
              <BrowserRouter>
                <Story />
              </BrowserRouter>
            </ConfigProvider>
          </ThemeProvider>
        </Provider>
      </QueryClientProvider>
    ),
  ],
};

export default preview;
