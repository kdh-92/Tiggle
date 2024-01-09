import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ReplyCellStyle = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  .content {
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    color: ${({ theme }) => theme.color.bluishGray[800].value};
  }

  ${({ theme }) => theme.mq.desktop} {
    .content {
      ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
    }
  }
`;
