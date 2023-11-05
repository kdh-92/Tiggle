import { theme } from "../theme";

const { color, fontSize } = theme;

const seedToken = {
  colorPrimary: color.blue[600].value,
  colorError: color.peach[600].value,
  colorSuccess: color.blue[600].value,
  colorWarning: color.green[600].value,
  colorTextBase: color.bluishGray[800].value,
  fontFamily: "'Pretendard Variable', Pretendard, system-ui, sans-serif",
  fontSize: fontSize.body.medium.value,
  lineWidth: 0,
};

const mapToken = {
  colorBgContainer: color.white.value,
  fontSizeLG: fontSize.body.large.value,
};

const aliasToken = {
  screenXS: 0,
  screenXSMin: 0,
  screenXSMax: 0,
  screenSM: 0,
  screenSMMin: 0,
  screenSMMax: 374,
  screenMD: 375,
  screenMDMin: 375,
  screenMDMax: 767,
  screenLG: 768,
  screenLGMin: 768,
  screenLGMax: Infinity,
  screenXL: Infinity,
  screenXLMin: Infinity,
  screenXLMax: Infinity,
  screenXXL: Infinity,
  screenXXLMin: Infinity,
};

const token = { ...seedToken, ...mapToken, ...aliasToken };

export default token;
