import { createRoot } from "react-dom/client";
import App from "./App";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";

const queryClient = new QueryClient();

const container = document.getElementById("root");
const root = createRoot(container);
root.render(
  <QueryClientProvider client={queryClient}>
    {process.env.NODE_ENV === 'development' && <ReactQueryDevtools /> }
    <App />
  </QueryClientProvider>
);
