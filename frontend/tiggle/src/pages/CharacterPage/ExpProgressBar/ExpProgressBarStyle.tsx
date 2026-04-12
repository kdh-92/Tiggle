import styled from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const ExpBarWrapper = styled.div`
  width: 100%;
  max-width: 327px;

  ${({ theme }) => theme.mq.desktop} {
    max-width: 480px;
  }
`;

export const ExpBarHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
`;

export const LevelBadge = styled.span`
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 2px 10px;
  border-radius: 12px;
  background: ${({ theme }) => theme.color.blue[600].value};
  color: ${({ theme }) => theme.color.white.value};
  ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
`;

export const ExpText = styled.span`
  color: ${({ theme }) => theme.color.bluishGray[500].value};
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
`;

export const BarTrack = styled.div`
  width: 100%;
  height: 12px;
  border-radius: 6px;
  background: ${({ theme }) => theme.color.bluishGray[100].value};
  overflow: hidden;
`;

export const BarFill = styled.div<{ $percent: number }>`
  height: 100%;
  border-radius: 6px;
  background: linear-gradient(
    90deg,
    ${({ theme }) => theme.color.blue[400].value},
    ${({ theme }) => theme.color.blue[600].value}
  );
  width: ${({ $percent }) => $percent}%;
  transition: width 0.5s ease;
  min-width: ${({ $percent }) => ($percent > 0 ? "4px" : "0")};
`;
