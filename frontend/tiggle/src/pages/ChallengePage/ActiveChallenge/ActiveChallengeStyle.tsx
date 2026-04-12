import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ActiveChallengeStyle = styled.div`
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 16px;
  padding: 24px;
  border: 2px solid #438bea;

  .challenge-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;

    .challenge-type {
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
      color: ${({ theme }) => theme.color.bluishGray[800].value};
    }
  }

  .progress-section {
    display: flex;
    align-items: center;
    gap: 24px;
    margin-bottom: 16px;

    .progress-ring {
      position: relative;
      width: 80px;
      height: 80px;
      flex-shrink: 0;

      svg {
        transform: rotate(-90deg);
      }

      .progress-bg {
        fill: none;
        stroke: ${({ theme }) => theme.color.bluishGray[100].value};
        stroke-width: 6;
      }

      .progress-fill {
        fill: none;
        stroke: #438bea;
        stroke-width: 6;
        stroke-linecap: round;
        transition: stroke-dashoffset 0.5s ease;
      }

      .progress-text {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
        color: ${({ theme }) => theme.color.bluishGray[700].value};
        text-align: center;
        line-height: 1.2;
      }
    }

    .progress-info {
      display: flex;
      flex-direction: column;
      gap: 8px;

      .days-text {
        ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
        color: #438bea;
      }

      .target-text {
        ${({ theme }) => expandTypography(theme.typography.body.medium.regular)}
        color: ${({ theme }) => theme.color.bluishGray[500].value};
      }
    }
  }

  .date-range {
    ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
    color: ${({ theme }) => theme.color.bluishGray[400].value};
    text-align: right;
  }

  .detail-link {
    display: block;
    text-align: center;
    margin-top: 16px;
    padding: 10px;
    border-radius: 8px;
    background-color: ${({ theme }) => theme.color.bluishGray[50].value};
    color: #438bea;
    ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    text-decoration: none;
    transition: background-color 0.2s;

    &:hover {
      background-color: ${({ theme }) => theme.color.bluishGray[100].value};
    }
  }
`;

export const StatusBadge = styled.span<{ $status: string }>`
  display: inline-block;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
  color: #fff;
  background-color: ${({ $status }) => {
    switch ($status) {
      case "ACTIVE":
        return "#438bea";
      case "COMPLETED":
        return "#28c79c";
      case "FAILED":
        return "#f25f5f";
      case "CANCELLED":
        return "#999";
      default:
        return "#999";
    }
  }};
`;
