import styled from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const InventoryPageContainer = styled.div`
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

export const TabContainer = styled.div`
  display: flex;
  gap: 0;
  margin-bottom: 24px;
  width: 100%;
  border-bottom: 1px solid ${({ theme }) => theme.color.bluishGray[200].value};
`;

export const TabButton = styled.button<{ $active: boolean }>`
  flex: 1;
  padding: 12px 0;
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  color: ${({ theme, $active }) =>
    $active ? theme.color.blue[600].value : theme.color.bluishGray[400].value};
  border-bottom: 2px solid
    ${({ theme, $active }) =>
      $active ? theme.color.blue[600].value : "transparent"};
  background: none;
  cursor: pointer;
  transition:
    color 0.15s ease,
    border-color 0.15s ease;

  &:hover {
    color: ${({ theme }) => theme.color.blue[600].value};
  }
`;

export const ItemGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
  width: 100%;

  ${({ theme }) => theme.mq.desktop} {
    grid-template-columns: repeat(4, 1fr);
    gap: 16px;
  }
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
  margin: 24px 0;
`;

export const EquipModalOverlay = styled.div`
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100;
`;

export const EquipModalContent = styled.div`
  background: ${({ theme }) => theme.color.white.value};
  border-radius: 16px;
  padding: 24px;
  width: 90%;
  max-width: 400px;
  max-height: 70vh;
  overflow-y: auto;
`;

export const EquipModalTitle = styled.h3`
  ${({ theme }) => expandTypography(theme.typography.title.small2x.bold)}
  color: ${({ theme }) => theme.color.bluishGray[800].value};
  margin-bottom: 16px;
`;

export const EquipModalItemList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

export const EquipModalItem = styled.button<{ $selected: boolean }>`
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border-radius: 8px;
  border: 2px solid
    ${({ theme, $selected }) =>
      $selected
        ? theme.color.blue[600].value
        : theme.color.bluishGray[200].value};
  background: ${({ theme, $selected }) =>
    $selected ? theme.color.blue[600].value + "0A" : theme.color.white.value};
  cursor: pointer;
  width: 100%;
  text-align: left;

  &:hover {
    border-color: ${({ theme }) => theme.color.blue[600].value};
  }
`;

export const EquipModalItemName = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  color: ${({ theme }) => theme.color.bluishGray[800].value};
`;

export const EquipModalActions = styled.div`
  display: flex;
  gap: 8px;
  margin-top: 16px;
`;

export const EquipModalButton = styled.button<{ $primary?: boolean }>`
  flex: 1;
  padding: 10px 16px;
  border-radius: 8px;
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  background: ${({ theme, $primary }) =>
    $primary ? theme.color.blue[600].value : theme.color.bluishGray[100].value};
  color: ${({ theme, $primary }) =>
    $primary ? theme.color.white.value : theme.color.bluishGray[600].value};
  cursor: pointer;
  transition: opacity 0.15s ease;

  &:hover {
    opacity: 0.85;
  }
`;
