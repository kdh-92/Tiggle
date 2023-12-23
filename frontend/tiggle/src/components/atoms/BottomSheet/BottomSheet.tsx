import { PropsWithChildren, useEffect, useRef, useState } from "react";

import cn from "classnames";

import { CTAButton } from "@/components/atoms";
import useOnClickOutside from "@/hooks/useOnClickOutside";

import {
  BottomSheetStyle,
  BottomSheetContainerStyle,
  BottomSheetHeaderStyle,
  BottomSheetFooterStyle,
} from "./BottomSheetStyle";

interface OverlayProps {
  isOpen: boolean;
}

interface BottomSheetProps extends PropsWithChildren, OverlayProps {
  header?: {
    title: React.ReactNode;
    reset?: boolean;
  };
  confirm: {
    label: string;
    onClick: () => void;
  };
  onClose: () => void;
}

const BottomSheet = ({
  isOpen,
  header,
  confirm,
  onClose,
  children,
}: BottomSheetProps) => {
  const [isMount, setIsMount] = useState(isOpen);
  const unmountRef = useRef<() => void | null>(null);
  const sheetRef = useRef<HTMLDivElement | null>(null);

  useOnClickOutside(sheetRef, onClose);

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
    <BottomSheetContainerStyle>
      <div className={cn("bottom-sheet-overlay", isOpen ? "open" : "close")} />

      <BottomSheetStyle
        ref={sheetRef}
        className={cn(isOpen ? "open" : "close")}
      >
        {header && (
          <BottomSheetHeaderStyle>
            <p className="title">{header.title}</p>
            {header.reset && <button className="reset">초기화</button>}
          </BottomSheetHeaderStyle>
        )}

        <div className="bottom-sheet-content">{children}</div>

        <BottomSheetFooterStyle>
          <CTAButton fullWidth size="lg" onClick={confirm.onClick}>
            {confirm.label}
          </CTAButton>
        </BottomSheetFooterStyle>
      </BottomSheetStyle>
    </BottomSheetContainerStyle>
  ) : null;
};

export default BottomSheet;
