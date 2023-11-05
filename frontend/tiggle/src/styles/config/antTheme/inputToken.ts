import { InputToken } from "antd/lib/input/style";

import { theme } from "../theme";

const { color, fontSize } = theme;

const inputToken: Partial<InputToken> = {
  // sizes
  fontSize: fontSize.body.large.value,
  fontSizeLG: fontSize.title.small2x.value,
  controlHeight: 54,
  controlHeightLG: 64,
  borderRadius: 12,
  borderRadiusLG: 16,
  paddingSM: 20,
  controlPaddingHorizontal: 24,
  // colors
  colorText: color.bluishGray[700].value,
  colorTextPlaceholder: color.bluishGray[400].value,
  colorTextDisabled: color.bluishGray[600].value,
  colorBgContainer: color.white.value,
  colorBgContainerDisabled: color.bluishGray[100].value,
};

export default inputToken;
