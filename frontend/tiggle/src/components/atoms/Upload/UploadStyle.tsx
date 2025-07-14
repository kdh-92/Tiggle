import styled from "styled-components";

import { expandTypography } from "../../../styles/util";

export const UploadStyle = styled.div`
  width: 100%;
  height: 108px;
  padding: 16px;
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 12px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 12px;

  &.filled {
    height: 240px;
  }

  &.error {
    border: 1px solid ${({ theme }) => theme.color.peach[300].value};
  }

  input[type="file"] {
    position: absolute;
    width: 0;
    height: 0;
    padding: 0;
    border: 0;
    overflow: hidden;
  }

  .view {
    width: 100%;
    height: auto;
    background-color: ${({ theme }) => theme.color.bluishGray[200].value};
    border-radius: 12px;
    overflow: hidden;
    pointer-events: none;

    > img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .view .remove-btn {
    pointer-events: auto;
  }

  .view .image-item {
    pointer-events: auto;
  }

  .controller {
    color: ${({ theme }) => theme.color.blue[500].value};
    display: flex;
    gap: 24px;
  }

  .upload {
    cursor: pointer;
    min-width: 80px;

    &-filled {
      ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
      display: flex;
      gap: 4px;
      white-space: nowrap;
    }

    &-empty {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 4px;
      white-space: nowrap;
    }
  }

  .reset {
    ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
    color: inherit;
    display: flex;
    gap: 2px;
  }

  ${({ theme }) => theme.mq.desktop} {
    width: 480px;
    height: 180px;
    border-radius: 16px;
    gap: 16px;

    &.filled {
      height: 300px;
    }

    .view {
      width: 360px;
      border-radius: 16px;
    }

    .controller {
      gap: 32px;
    }

    .upload {
      &-filled {
        ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
      }

      &-empty {
        ${({ theme }) => expandTypography(theme.typography.body.large.regular)}
        gap: 4px;
      }
    }

    .reset {
      ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
    }

    .image-item {
      position: relative;
      display: inline-block;
      margin: 12px;
    }

    .image-item:hover .remove-btn {
      display: flex;
    }

    .image-item img {
      width: 80px;
      height: 80px;
      object-fit: cover;
      border-radius: 4px;
      border: 1px solid #ddd;
    }

    .remove-btn {
      position: absolute;
      top: -8px;
      right: -8px;
      background: red;
      color: white;
      border: none;
      border-radius: 50%;
      width: 24px;
      height: 24px;
    }

    .upload-style .image-item {
      position: relative;
    }

    /* 기존/새로운 이미지 구분을 위한 배지 */
    .upload-style .image-badge {
      position: absolute;
      top: 4px;
      left: 4px;
      background: rgba(0, 0, 0, 0.7);
      color: white;
      font-size: 10px;
      padding: 2px 6px;
      border-radius: 4px;
      font-weight: 500;
      z-index: 2;
    }

    .upload-style .image-badge.new {
      background: rgba(16, 185, 129, 0.9); /* green-500 */
    }

    /* 삭제 버튼 스타일 개선 */
    .upload-style .remove-btn {
      position: absolute;
      top: 4px;
      right: 4px;
      background: rgba(239, 68, 68, 0.9); /* red-500 */
      color: white;
      border: none;
      border-radius: 50%;
      width: 24px;
      height: 24px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      z-index: 3;
      transition: all 0.2s ease;
    }

    .upload-style .remove-btn:hover {
      background: rgba(239, 68, 68, 1);
      transform: scale(1.1);
    }

    .upload-style .remove-btn.existing {
      background: rgba(59, 130, 246, 0.9); /* blue-500 */
    }

    .upload-style .remove-btn.existing:hover {
      background: rgba(59, 130, 246, 1);
    }

    /* 이미지 아이템 호버 효과 */
    .upload-style .image-item:hover {
      transform: scale(1.02);
      transition: transform 0.2s ease;
    }

    .upload-style .image-item img {
      transition: opacity 0.2s ease;
    }

    .upload-style .image-item:hover img {
      opacity: 0.8;
    }

    /* 편집 모드 안내 텍스트 */
    .edit-mode-info {
      display: flex;
      align-items: center;
      gap: 4px;
      font-size: 12px;
      color: #666;
      margin-top: 8px;
    }

    .edit-mode-info > div {
      display: flex;
      align-items: center;
      gap: 4px;
    }

    .edit-mode-info > div:not(:first-child) {
      margin-left: 12px;
    }

    /* 반응형 대응 */
    @media (max-width: 768px) {
      .upload-style .remove-btn {
        width: 20px;
        height: 20px;
      }

      .upload-style .image-badge {
        font-size: 8px;
        padding: 1px 4px;
      }

      .edit-mode-info {
        flex-direction: column;
        align-items: flex-start;
        gap: 4px;
      }

      .edit-mode-info > div:not(:first-child) {
        margin-left: 0;
      }
    }
  }
`;

export const ErrorMessageStyle = styled.span`
  color: ${({ theme }) => theme.color.peach[500].value};
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
`;
