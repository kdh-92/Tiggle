import styled from "styled-components";

import { expandTypography } from "../util";

export const CommentFormStyle = styled.form`
  padding: 24px;
  background-color: ${({ theme }) => theme.color.white.value};
  border: 1px solid ${({ theme }) => theme.color.peach[200].value};
  border-radius: 12px;

  display: flex;
  flex-direction: column;
  gap: 16px;

  textarea.comment {
    width: 100%;
    border: none;
    outline: none;
    resize: none;
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)};
    color: ${({ theme }) => theme.color.bluishGray[800].value};

    &::placeholder {
      color: ${({ theme }) => theme.color.bluishGray[400].value};
    }
  }

  .button-wrapper {
    display: flex;
    justify-content: flex-end;
  }

  ${({ theme }) => theme.mq.desktop} {
    padding: 28px;
    border-radius: 16px;
    textarea.comment {
      ${({ theme }) => expandTypography(theme.typography.body.large.medium)};
    }
  }
`;
