import styled from "styled-components";

import { expandTypography } from "../../../styles/util";

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

  .form-controller {
    padding-top: 24px;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
  }

  ${({ theme }) => theme.mq.desktop} {
    .form-item {
      > label {
        ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
      }
    }
    .form-controller {
      padding-top: 32px;
      gap: 16px;
    }
  }
`;
