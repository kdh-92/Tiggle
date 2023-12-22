import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const HashTagStyle = styled.li`
  width: fit-content;
  padding: 4px 8px;
  background-color: ${({ theme }) => theme.color.bluishGray[50].value};
  color: ${({ theme }) => theme.color.bluishGray[600].value};
  border-radius: 50px;

  display: flex;
  align-items: center;
  gap: 2px;

  > span {
    ${({ theme }) => expandTypography(theme.typography.body.small.medium)}

    ${({ theme }) => theme.mq.desktop} {
      ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    }
  }
`;
