import styled from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const AchievementPageContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 16px 80px;
  max-width: 720px;
  margin: 0 auto;
  width: 100%;
`;

export const PageTitle = styled.h2`
  ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
  color: ${({ theme }) => theme.color.bluishGray[800].value};
  margin-bottom: 24px;
  text-align: center;

  ${({ theme }) => theme.mq.desktop} {
    ${({ theme }) => expandTypography(theme.typography.title.large.bold)}
  }
`;

export const SectionTitle = styled.h3`
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  color: ${({ theme }) => theme.color.bluishGray[700].value};
  margin-bottom: 12px;
  width: 100%;

  ${({ theme }) => theme.mq.desktop} {
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
  }
`;

export const RecentSection = styled.div`
  width: 100%;
  margin-bottom: 32px;
`;

export const AllSection = styled.div`
  width: 100%;
`;

export const AchievementList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 12px;
  width: 100%;
`;

export const EmptyMessage = styled.p`
  ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
  color: ${({ theme }) => theme.color.bluishGray[400].value};
  text-align: center;
  padding: 40px 0;
`;

export const SectionDivider = styled.hr`
  width: 100%;
  border: none;
  border-top: 1px solid ${({ theme }) => theme.color.bluishGray[100].value};
  margin: 8px 0 24px;
`;

export const StatsSummary = styled.div`
  display: flex;
  gap: 16px;
  width: 100%;
  margin-bottom: 24px;
`;

export const StatBox = styled.div`
  flex: 1;
  padding: 16px;
  border-radius: 12px;
  background: ${({ theme }) => theme.color.white.value};
  border: 1px solid ${({ theme }) => theme.color.bluishGray[100].value};
  text-align: center;
`;

export const StatNumber = styled.span`
  ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
  color: ${({ theme }) => theme.color.blue[600].value};
  display: block;
`;

export const StatLabel = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  color: ${({ theme }) => theme.color.bluishGray[500].value};
`;
