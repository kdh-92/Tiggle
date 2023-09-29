import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const SocialLoginButtonStyle = styled.button`
  width: 300px;
  height: 45px;
  border-radius: 6px;
  padding: 0 14px;
  display: flex;
  align-items: center;
  margin-bottom: 16px;

  /* TODO: figma 상에서는 semibold로 되어 있어서 가장 비슷한 것으로 폰트 지정 */
  ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}

  > span {
    display: inline;
    width: 254px;
  }

  /* TODO: figma token에 값 설정되면 변경 */
  &.kakao {
    background-color: #fee500;
  }

  &.naver {
    background-color: #03c75a;
    color: #fff;
  }

  &.google {
    background-color: #fff;
    color: #757575;
  }
`;
