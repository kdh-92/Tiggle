import { Grid } from "antd";

const { useBreakpoint } = Grid;

export const isDesktop = () => {
  const screens = useBreakpoint();
  return Object.entries(screens)
    .filter(([, v]) => !!v)
    .map(([k]) => k)
    .includes("lg");
};
