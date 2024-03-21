import { render, RenderOptions } from "@testing-library/react";
import { Provider } from "react-redux";
import { store, persistedStore } from "@/store";
import { cloneElement, PropsWithChildren } from "react";
import { BrowserRouter } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import { mq } from "@/styles/config/mediaQueries";
import { theme } from "@/styles/config/theme";
import { QueryClientProvider } from "@tanstack/react-query";
import { CookiesProvider } from "react-cookie";
import queryClient from "@/query/queryClient";
import MessageProvider from "@/hooks/useMessage/MessageProvider";

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

type Ui = Parameters<typeof render>[0];

const renderWithContext = (ui: Ui, options?: RenderOptions) => {
  render(
    <Provider store={store}>
      <MultiProviders providers={[
        <QueryClientProvider client={queryClient} />,
        <CookiesProvider />,
        <ThemeProvider theme={{ ...theme, mq }} />,
        <BrowserRouter />,
        <MessageProvider />,
      ]}>
        {ui}
      </MultiProviders>
    </Provider>
  , options)
}

export default renderWithContext;