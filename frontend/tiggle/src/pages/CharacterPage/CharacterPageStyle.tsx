import { Link } from "react-router-dom";

import styled from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const CharacterPageStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 20px 80px;
  gap: 32px;

  ${({ theme }) => theme.mq.desktop} {
    padding: 60px 20px 120px;
    gap: 40px;
  }
`;

export const PageTitle = styled.h1`
  ${({ theme }) => expandTypography(theme.typography.title.medium.bold)}
  color: ${({ theme }) => theme.color.bluishGray[800].value};
  text-align: center;

  ${({ theme }) => theme.mq.desktop} {
    ${({ theme }) => expandTypography(theme.typography.title.large.bold)}
  }
`;

export const CharacterSection = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;
  width: 100%;
  max-width: 327px;

  ${({ theme }) => theme.mq.desktop} {
    max-width: 480px;
  }
`;

export const InfoCard = styled.div`
  width: 100%;
  padding: 20px;
  background: ${({ theme }) => theme.color.white.value};
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
`;

export const InfoRow = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const InfoLabel = styled.span`
  color: ${({ theme }) => theme.color.bluishGray[500].value};
  ${({ theme }) => expandTypography(theme.typography.body.medium.medium)}
`;

export const InfoValue = styled.span`
  color: ${({ theme }) => theme.color.bluishGray[800].value};
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
`;

export const CatalogLink = styled(Link)`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  max-width: 327px;
  padding: 16px;
  border-radius: 12px;
  background: ${({ theme }) => theme.color.white.value};
  color: ${({ theme }) => theme.color.blue[600].value};
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  text-decoration: none;
  transition: background 0.2s ease;
  gap: 4px;

  &:hover {
    background: ${({ theme }) => theme.color.blue[50].value};
  }

  ${({ theme }) => theme.mq.desktop} {
    max-width: 480px;
    ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
  }
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
