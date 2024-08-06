import { Upload } from "antd";
import styled, { css } from "styled-components";

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

export const CreateFormStyle2 = styled.form`
  position: relative;

  .form-item {
    display: flex;
    flex-direction: column;
    gap: 8px;
    padding-bottom: 32px;

    > label {
      ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
      color: ${({ theme }) => theme.color.bluishGray[600].value}
    }
  }

  .form-dialog {
    display: flex;
    flex-direction: column;
    gap: 32px;
    padding-bottom: 80px;

    > label {
      ${({ theme }) => expandTypography(theme.typography.title.small.medium)}
      color: ${({ theme }) => theme.color.bluishGray[700].value};
      text-align: center;
    }

    &-controllers {
      display: flex;
      flex-direction: column;
      gap: 8px;
    }
  }

  .form-submit {
    position: sticky;
    bottom: 30px;
    z-index: 9;

    width: 100%;
  }
`;
