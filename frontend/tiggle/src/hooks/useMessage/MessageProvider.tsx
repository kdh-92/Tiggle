import { PropsWithChildren, createContext, useMemo } from "react";

import { message } from "antd";
import { MessageInstance } from "antd/lib/message/interface";

interface MessageState {
  api: MessageInstance;
}

export const mockApi = {
  open: (args: any) => console.log(args),
  info: (args: any) => console.log(args),
  success: (args: any) => console.log(args),
  error: (args: any) => console.log(args),
  warning: (args: any) => console.log(args),
  loading: (args: any) => console.log(args),
} as MessageInstance;

export const MessageContext = createContext<MessageState>({ api: mockApi });
if (process.env.NODE_ENV !== "production") {
  MessageContext.displayName = "MessageContext";
}

const MessageProvider = ({ children }: PropsWithChildren) => {
  const [messageApi, contextHolder] = message.useMessage();

  const context = useMemo(() => ({ api: messageApi }), [messageApi]);

  return (
    <MessageContext.Provider value={context}>
      {contextHolder}
      {children}
    </MessageContext.Provider>
  );
};

export default MessageProvider;
