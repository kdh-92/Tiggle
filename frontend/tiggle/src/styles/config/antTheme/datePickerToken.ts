import { PickerPanelToken } from "antd/lib/date-picker/style";
import { FullToken } from "antd/lib/theme/util/genComponentStyleHook";

import { theme } from "../theme";

type DatePickerToken = FullToken<"DatePicker"> & PickerPanelToken;

const { fontSize, color } = theme;

const datePickerToken: Partial<DatePickerToken> = {
  // sizes
  fontSize: fontSize.body.large.value,
  fontSizeLG: fontSize.title.small2x.value,
  borderRadius: 12,
  borderRadiusLG: 16,
  controlHeight: 54,
  controlHeightLG: 64,
  paddingSM: 20,
  controlPaddingHorizontal: 24,
  // colors
  colorText: color.bluishGray[700].value,
  colorTextPlaceholder: color.bluishGray[400].value,
  colorTextDisabled: color.bluishGray[600].value,
  colorBgContainer: color.white.value,
  colorBgContainerDisabled: color.bluishGray[100].value,
  colorError: color.peach[300].value,
};

export default datePickerToken;
