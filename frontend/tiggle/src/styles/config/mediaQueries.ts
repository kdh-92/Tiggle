export type Device = "mobile" | "desktop";

const breakPoint = 768;

export const mq: Record<Device, string> = {
  mobile: `@media only screen and (max-width: ${breakPoint - 1}px)`,
  desktop: `@media only screen and (min-width: ${breakPoint}px)`,
};
