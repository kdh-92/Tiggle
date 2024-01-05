import { PropsWithChildren, createContext, useMemo } from "react";

import { message } from "antd";
import { MessageInstance } from "antd/lib/message/interface";

interface MessageState {
  api: MessageInstance;
}

export const MessageContext = createContext<MessageState>({ api: undefined });
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
