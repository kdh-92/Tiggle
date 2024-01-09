import { PropsWithChildren, useEffect, useRef, useState } from "react";

import cn from "classnames";

import { PopoverStyle, PopoverHeaderStyle } from "./PopOverStyle";

interface OverlayProps {
  isOpen: boolean;
}

interface PopOverProps extends PropsWithChildren, OverlayProps {
  header?: {
    title: React.ReactNode;
    reset?: boolean;
  };
}

const PopOver = ({ isOpen, header, children }: PopOverProps) => {
  const [isMount, setIsMount] = useState(isOpen);
  const unmountRef = useRef<() => void | null>(null);

  useEffect(() => {
    if (isOpen) {
      setIsMount(true);
    }
    unmountRef.current = () => {
      if (!isOpen) {
        setIsMount(false);
      }
    };
  }, [isOpen]);

  useEffect(() => {
    const animationendId = () => unmountRef.current?.();
    window.addEventListener("animationend", animationendId);
    return () => window.removeEventListener("animationend", animationendId);
  }, []);

  return isMount ? (
    <PopoverStyle className={cn(isOpen ? "open" : "close")}>
      {header && (
        <PopoverHeaderStyle>
          <p className="title">{header.title}</p>
          {header.reset && <button className="reset">초기화</button>}
        </PopoverHeaderStyle>
      )}
      <div className="popover-content">{children}</div>
    </PopoverStyle>
  ) : null;
};

export default PopOver;
