import styled from "styled-components";

import { expandTypography } from "../util";

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
    }
  }
`;

export const CreateFormStyle = styled.form`
  .form-item {
    display: flex;
    flex-direction: column;
    gap: 8px;
    margin-bottom: 32px;

    > label {
      ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
      color: ${({ theme }) => theme.color.bluishGray[600].value}
    }
  }

  .form-divider {
    width: 100%;
    height: 1px;
    min-height: 1px;
    margin: 16px 0;
    background-color: ${({ theme }) => theme.color.bluishGray[200].value};
  }

  .form-item-caption {
    display: flex;
    align-items: center;
    gap: 4px;
    ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
    color: ${({ theme }) => theme.color.bluishGray[400].value}
  }
`;
