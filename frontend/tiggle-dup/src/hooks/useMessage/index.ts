import { useContext } from "react";

import { MessageContext } from "./MessageProvider";

const useMessage = () => {
  const context = useContext(MessageContext);

  return context.api;
};

export default useMessage;
