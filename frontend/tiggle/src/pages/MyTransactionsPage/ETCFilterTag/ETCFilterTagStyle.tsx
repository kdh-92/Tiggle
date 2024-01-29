import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ETCFilterTagStyle = styled.div`
  padding: 4px 10px;
  color: ${({ theme }) => theme.color.white.value};
  background-color: ${({ theme }) => theme.color.bluishGray[700].value};
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  border-radius: 50px;

  display: flex;
  align-items: center;
  gap: 4px;

  button > svg {
    width: 12px;
    height: 12px;
    color: ${({ theme }) => theme.color.bluishGray[400].value};
  }
`;
