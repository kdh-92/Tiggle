import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ProfileImageSectionStyle = styled.div`
  margin-bottom: 48px;

  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;

  .profile-avatar {
    position: relative;

    .ant-avatar {
      background-color: ${({ theme }) => theme.color.bluishGray[300].value};
      background-image: url("/assets/user-placeholder.png");
      background-size: cover;
    }
  }

  .profile-edit {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.color.bluishGray[200].value};
    box-shadow: 0 0 0 3px ${({ theme }) => theme.color.bluishGray[50].value};

    display: flex;
    align-items: center;
    justify-content: center;

    position: absolute;
    bottom: 4px;
    right: -4px;
    cursor: pointer;

    > svg {
      width: 20px;
      height: 20px;
      color: ${({ theme }) => theme.color.bluishGray[600].value};
    }

    &:hover {
      background-color: ${({ theme }) => theme.color.bluishGray[300].value};
    }
  }

  input[type="file"] {
    position: absolute;
    width: 0;
    height: 0;
    padding: 0;
    border: 0;
  }

  .profile-delete-btn {
    width: 100px;
    padding: 8px 0;
    border-radius: 8px;

    ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
    color: ${({ theme }) => theme.color.bluishGray[600].value};
    background-color: ${({ theme }) => theme.color.bluishGray[100].value};

    &:hover {
      background-color: ${({ theme }) => theme.color.bluishGray[200].value};
    }
  }

  ${({ theme }) => theme.mq.desktop} {
    margin-bottom: 64px;

    .profile-edit-btn {
      width: 42px;
      height: 42px;

      > svg {
        width: 24px;
        height: 24px;
        color: ${({ theme }) => theme.color.bluishGray[600].value};
      }
    }

    .profile-delete-btn {
      width: 120px;
      ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    }
  }
`;

export const ProfileFormItemStyle = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 24px;

  > label {
    ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
    color: ${({ theme }) => theme.color.bluishGray[600].value}
  }

  ${({ theme }) => theme.mq.desktop} {
    margin-bottom: 32px;
    > label {
      ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
    }
  }
`;

export const ProfileFormControllerStyle = styled.div`
  padding-top: 24px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;

  ${({ theme }) => theme.mq.desktop} {
    padding-top: 32px;
    gap: 16px;
  }
`;
