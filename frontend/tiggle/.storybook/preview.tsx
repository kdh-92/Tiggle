import type { Preview } from "@storybook/react";
import { Provider } from "react-redux";

import { ThemeProvider } from "styled-components";
import { ConfigProvider } from "antd";

import { theme } from "@/styles/config/theme";
import { antTheme } from "@/styles/config/antTheme";
import { GlobalStyle } from "@/styles/config/GlobalStyle";
import { mq } from "@/styles/config/mediaQueries";
import { store } from "@/store";



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
      <Provider store={store}>
        <ThemeProvider theme={{ ...theme, mq }}>
          <ConfigProvider theme={antTheme}>
            <GlobalStyle />
            <Story />
          </ConfigProvider>
        </ThemeProvider>
      </Provider>
    ),
  ],
};

export default preview;
