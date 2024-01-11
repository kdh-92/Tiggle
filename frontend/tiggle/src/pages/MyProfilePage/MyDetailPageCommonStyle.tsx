import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const MypageDetailPageStyle = styled.div`
  width: 100%;
  padding: 24px;

  display: flex;
  flex-direction: column;
  align-items: stretch;

  .page-title {
    margin-bottom: 48px;
    color: ${({ theme }) => theme.color.bluishGray[800].value};
    ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
    text-align: left;
  }

  ${({ theme }) => theme.mq.desktop} {
    width: 480px;
    margin: 0 auto;
    padding: 32px 0;

    .page-title {
      margin-bottom: 64px;
      ${({ theme }) => expandTypography(theme.typography.title.large.bold)}
      text-align: center;
    }
  }
`;
