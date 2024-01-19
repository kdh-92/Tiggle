import styled from "styled-components";

import { expandTypography } from "../../styles/util";

export const CreatePageStyle = styled.div`
  width: 100%;
  margin: 0 auto;
  padding: 24px;

  .title {
    ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
    color: ${({ theme }) => theme.color.bluishGray[800].value};
    margin-bottom: 40px;
  }

  ${({ theme }) => theme.mq.desktop} {
    width: 480px;
    padding: 32px 0;

    .title {
      margin-bottom: 64px;
      text-align: center;
    }
  }
`;
