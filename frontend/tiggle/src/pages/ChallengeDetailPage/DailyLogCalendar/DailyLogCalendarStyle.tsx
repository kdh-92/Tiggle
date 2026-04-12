import styled from "styled-components";

import { expandTypography } from "@/styles/util";

export const DailyLogCalendarStyle = styled.div`
  background-color: ${({ theme }) => theme.color.white.value};
  border-radius: 16px;
  padding: 20px;

  .weekday-header {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    gap: 4px;
    margin-bottom: 8px;

    .weekday {
      text-align: center;
      ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
      color: ${({ theme }) => theme.color.bluishGray[400].value};
      padding: 4px 0;
    }
  }

  .calendar-grid {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    gap: 4px;
  }
`;

export const DayCell = styled.div<{
  $type: "no-spend" | "spent" | "future" | "empty";
}>`
  aspect-ratio: 1;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 2px;
  background-color: ${({ $type, theme }) => {
    switch ($type) {
      case "no-spend":
        return "#e8f5e9";
      case "spent":
        return "#ffebee";
      case "future":
        return theme.color.bluishGray[50].value;
      case "empty":
        return "transparent";
      default:
        return "transparent";
    }
  }};
  border: ${({ $type }) => {
    switch ($type) {
      case "no-spend":
        return "1px solid #28c79c";
      case "spent":
        return "1px solid #f25f5f";
      case "future":
        return "1px solid transparent";
      case "empty":
        return "none";
      default:
        return "none";
    }
  }};

  .day-number {
    font-size: 12px;
    font-weight: 600;
    color: ${({ $type, theme }) => {
      switch ($type) {
        case "no-spend":
          return "#28c79c";
        case "spent":
          return "#f25f5f";
        case "future":
          return theme.color.bluishGray[300].value;
        default:
          return "transparent";
      }
    }};
  }

  .day-amount {
    font-size: 9px;
    font-weight: 500;
    color: #f25f5f;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 100%;
    padding: 0 2px;
  }
`;
