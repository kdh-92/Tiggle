import { PropsWithChildren, useEffect, useRef, useState } from "react";

import cn from "classnames";

import useOnClickOutside from "@/hooks/useOnClickOutside";

import { OverlayContainerStyle, AlertStyle } from "./AlertStyle";
import CTAButton from "../CTAButton/CTAButton";

interface OverlayProps {
  isOpen: boolean;
}

interface AlertProps extends PropsWithChildren, OverlayProps {
  onClose: () => void;
  confirm: {
    label: string;
    onClick: () => void;
  };
  cancel?: {
    label: string;
    onClick: () => void;
  };
}

const Alert = ({ isOpen, children, onClose, confirm, cancel }: AlertProps) => {
  const [isMount, setIsMount] = useState(isOpen);
  const unmountRef = useRef<(() => void) | null>(null);
  const alertRef = useRef<HTMLDivElement | null>(null);
  useOnClickOutside(alertRef, onClose);

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
    <OverlayContainerStyle>
      <div className={cn("overlay", isOpen ? "open" : "close")} />

      <AlertStyle ref={alertRef} className={cn(isOpen ? "open" : "close")}>
        <div className="alert-content">{children}</div>
        <div className="alert-footer">
          {cancel && (
            <CTAButton
              className="cancelBtn"
              variant="light"
              color="bluishGray"
              fullWidth
              onClick={cancel.onClick}
            >
              {cancel.label}
            </CTAButton>
          )}
          <CTAButton className="confirmBtn" fullWidth onClick={confirm.onClick}>
            {confirm.label}
          </CTAButton>
        </div>
      </AlertStyle>
    </OverlayContainerStyle>
  ) : null;
};

export default Alert;
