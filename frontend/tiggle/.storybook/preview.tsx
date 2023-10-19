import type { Preview } from "@storybook/react";
import { ThemeProvider } from "styled-components";
import { theme } from "@/styles/config/theme";
import { GlobalStyle } from "@/styles/config/GlobalStyle";
import { mq } from "@/styles/config/mediaQueries";
import { QueryClientProvider } from "@tanstack/react-query";
import queryClient from "@/query/queryClient";
import { Provider } from "react-redux";
import { store } from "@/store";
import { BrowserRouter } from 'react-router-dom';


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
              <GlobalStyle />
              <BrowserRouter>
                <Story />
              </BrowserRouter>
            </ThemeProvider>
          </Provider>
        </QueryClientProvider>
    ),
  ],
};

export default preview;
