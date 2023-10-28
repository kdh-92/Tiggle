import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ReactionSectionStyle = styled.div`
  width: 100%;
  padding: 24px;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 12px;

  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;

  .title {
    ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
    color: ${({ theme }) => theme.color.bluishGray[700].value};
    text-align: center;
  }

  .button-wrapper {
    display: flex;
    gap: 8px;
  }

  ${({ theme }) => theme.mq.desktop} {
    padding: 28px;
    gap: 24px;

    .title {
      ${({ theme }) => expandTypography(theme.typography.title.small2x.medium)}
    }

    .button-wrapper {
      gap: 16px;
    }
  }
`;
