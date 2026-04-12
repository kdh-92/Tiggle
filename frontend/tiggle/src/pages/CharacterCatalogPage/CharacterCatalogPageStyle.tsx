import styled from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const CatalogPageStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 20px 80px;
  gap: 32px;
  width: 100%;

  ${({ theme }) => theme.mq.desktop} {
    padding: 60px 20px 120px;
    gap: 40px;
  }
`;

export const CatalogTitle = styled.h1`
  ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
  color: ${({ theme }) => theme.color.bluishGray[800].value};
  text-align: center;

  ${({ theme }) => theme.mq.desktop} {
    ${({ theme }) => expandTypography(theme.typography.title.large.bold)}
  }
`;

export const PathSection = styled.section`
  width: 100%;
  max-width: 720px;
`;

export const PathHeader = styled.button<{ $expanded: boolean }>`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 16px 20px;
  background: ${({ theme }) => theme.color.white.value};
  border-radius: ${({ $expanded }) => ($expanded ? "16px 16px 0 0" : "16px")};
  transition: border-radius 0.2s ease;

  &:hover {
    background: ${({ theme }) => theme.color.bluishGray[50].value};
  }
`;

export const PathLabel = styled.span`
  ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)}
  color: ${({ theme }) => theme.color.bluishGray[800].value};
`;

export const PathCount = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
  color: ${({ theme }) => theme.color.bluishGray[400].value};
`;

export const ChevronIcon = styled.span<{ $expanded: boolean }>`
  display: flex;
  align-items: center;
  color: ${({ theme }) => theme.color.bluishGray[400].value};
  transform: rotate(${({ $expanded }) => ($expanded ? "90deg" : "0deg")});
  transition: transform 0.2s ease;
`;

export const PathHeaderLeft = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

export const FormGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1px;
  background: ${({ theme }) => theme.color.bluishGray[100].value};
  border-radius: 0 0 16px 16px;
  overflow: hidden;

  ${({ theme }) => theme.mq.desktop} {
    grid-template-columns: repeat(3, 1fr);
  }
`;

export const FormCard = styled.div<{ $tierColor: string }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 20px 12px;
  background: ${({ theme }) => theme.color.white.value};
  text-align: center;
`;

export const FormImagePlaceholder = styled.div<{ $tierColor: string }>`
  width: 64px;
  height: 64px;
  border-radius: 50%;
  background: ${({ $tierColor }) => $tierColor}22;
  border: 2px solid ${({ $tierColor }) => $tierColor};
  display: flex;
  align-items: center;
  justify-content: center;

  ${({ theme }) => theme.mq.desktop} {
    width: 80px;
    height: 80px;
  }
`;

export const FormLevel = styled.span<{ $tierColor: string }>`
  ${({ theme }) => expandTypography(theme.typography.body.small2x.bold)}
  color: ${({ $tierColor }) => $tierColor};
`;

export const FormName = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  color: ${({ theme }) => theme.color.bluishGray[800].value};
`;

export const FormExp = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  color: ${({ theme }) => theme.color.bluishGray[400].value};
`;

export const TierDot = styled.span<{ $color: string }>`
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: ${({ $color }) => $color};
`;

export const EmptyState = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  padding: 60px 20px;
  color: ${({ theme }) => theme.color.bluishGray[400].value};
  ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
  text-align: center;
`;

export const BackLink = styled.a`
  color: ${({ theme }) => theme.color.blue[600].value};
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  text-decoration: none;

  &:hover {
    text-decoration: underline;
  }
`;
