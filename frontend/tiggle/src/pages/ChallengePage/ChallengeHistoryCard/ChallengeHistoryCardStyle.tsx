import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const ChallengeHistoryCardStyle = styled.div<{ $status: string }>`
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 12px;
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
  transition: background-color 0.2s;
  border-left: 4px solid
    ${({ $status }) => {
      switch ($status) {
        case "COMPLETED":
          return "#28c79c";
        case "FAILED":
          return "#f25f5f";
        case "CANCELLED":
          return "#999";
        default:
          return "#438bea";
      }
    }};

  &:hover {
    background-color: ${({ theme }) => theme.color.bluishGray[50].value};
  }

  .card-left {
    display: flex;
    flex-direction: column;
    gap: 4px;

    .card-type {
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
      color: ${({ theme }) => theme.color.bluishGray[800].value};
    }

    .card-date {
      ${({ theme }) => expandTypography(theme.typography.body.small.regular)}
      color: ${({ theme }) => theme.color.bluishGray[400].value};
    }
  }

  .card-right {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    gap: 4px;

    .card-days {
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
      color: ${({ theme }) => theme.color.bluishGray[700].value};
    }
  }
`;
