import { SelectToken } from "antd/lib/select/style/token";

import { theme } from "../theme";

const { color, fontSize } = theme;

const selectToken: Partial<SelectToken> = {
  // sizes
  fontSize: fontSize.body.large.value,
  fontSizeLG: fontSize.title.small2x.value,
  controlHeight: 54,
  controlHeightLG: 64,
  borderRadius: 12,
  borderRadiusLG: 16,
  // colors
  colorText: color.bluishGray[600].value,
  colorTextPlaceholder: color.blue[500].value,
  colorTextDisabled: color.bluishGray[600].value,
  controlItemBgActive: color.blue[50].value,
  controlItemBgHover: color.bluishGray[50].value,
  colorBgBase: color.white.value,
  colorBgContainerDisabled: color.bluishGray[100].value,
  colorError: color.peach[300].value,
};

export default selectToken;
