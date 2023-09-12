import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ReactionSectionStyle = styled.div`
  width: fit-content;
  padding: 24px;
  border: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};
  border-radius: 24px;

  display: flex;
  flex-direction: column;
  gap: 16px;

  .title {
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
    color: ${({ theme }) => theme.color.bluishGray[700].value};
    text-align: left;
  }

  .button-wrapper {
    display: flex;
    gap: 8px;
  }

  ${({ theme }) => theme.mq.desktop} {
    padding: 24px 32px;
    gap: 24px;

    .title {
      text-align: center;
      ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)}
    }

    .button-wrapper {
      gap: 16px;
    }
  }
`;
