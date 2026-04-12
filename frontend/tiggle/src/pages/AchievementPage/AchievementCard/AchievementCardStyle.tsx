import styled, { css } from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const CardContainer = styled.div<{ $achieved: boolean }>`
  display: flex;
  align-items: flex-start;
  gap: 12px;
  padding: 16px;
  border-radius: 12px;
  background: ${({ theme }) => theme.color.white.value};
  border: 1px solid ${({ theme }) => theme.color.bluishGray[100].value};
  transition: box-shadow 0.15s ease;

  ${({ $achieved }) =>
    !$achieved &&
    css`
      opacity: 0.5;
      filter: grayscale(60%);
    `}

  &:hover {
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  }
`;

export const AchievementIcon = styled.div<{ $achieved: boolean }>`
  width: 44px;
  height: 44px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  flex-shrink: 0;
  background: ${({ theme, $achieved }) =>
    $achieved
      ? theme.color.blue[600].value + "1A"
      : theme.color.bluishGray[100].value};
`;

export const CardBody = styled.div`
  flex: 1;
  min-width: 0;
`;

export const AchievementName = styled.h4`
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  color: ${({ theme }) => theme.color.bluishGray[800].value};
  margin-bottom: 4px;
`;

export const AchievementDescription = styled.p`
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  color: ${({ theme }) => theme.color.bluishGray[500].value};
  margin-bottom: 6px;
`;

export const AchievementCondition = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  color: ${({ theme }) => theme.color.bluishGray[400].value};
`;

export const AchievedDate = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  color: ${({ theme }) => theme.color.blue[600].value};
  display: flex;
  align-items: center;
  gap: 4px;
  margin-top: 4px;
`;

export const ProgressBar = styled.div`
  width: 100%;
  height: 6px;
  border-radius: 3px;
  background: ${({ theme }) => theme.color.bluishGray[100].value};
  margin-top: 8px;
  overflow: hidden;
`;

export const ProgressFill = styled.div<{ $percent: number }>`
  height: 100%;
  border-radius: 3px;
  background: ${({ theme }) => theme.color.blue[600].value};
  width: ${({ $percent }) => Math.min($percent, 100)}%;
  transition: width 0.3s ease;
`;
