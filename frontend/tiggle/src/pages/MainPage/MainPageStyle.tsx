import styled, { css } from "styled-components";

interface MainPageStyleProps {
  open: boolean;
}

export const MainPageStyle = styled.div<MainPageStyleProps>`
  min-height: 100vh;
  position: relative;
  color: ${({ theme }) => theme.color.bluishGray[800].value};

  ${({ theme, open }) =>
    open &&
    css`
      ${theme.mq.mobile} {
        display: none;
      }
    `}
`;
