import styled from "styled-components";

import { expandTypography } from "@/styles/util/expandTypography";

export const EquipmentContainer = styled.div`
  width: 100%;
  margin-bottom: 24px;
`;

export const EquipmentTitle = styled.h3`
  ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
  color: ${({ theme }) => theme.color.bluishGray[700].value};
  margin-bottom: 12px;
`;

export const SlotsGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;

  ${({ theme }) => theme.mq.desktop} {
    grid-template-columns: repeat(6, 1fr);
  }
`;

export const SlotBox = styled.button<{ $hasItem: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 6px;
  padding: 12px 8px;
  border-radius: 12px;
  border: 2px dashed
    ${({ theme, $hasItem }) =>
      $hasItem
        ? theme.color.blue[600].value
        : theme.color.bluishGray[200].value};
  background: ${({ theme, $hasItem }) =>
    $hasItem ? theme.color.blue[600].value + "0A" : theme.color.white.value};
  cursor: pointer;
  transition:
    border-color 0.15s ease,
    background 0.15s ease;
  min-height: 88px;

  &:hover {
    border-color: ${({ theme }) => theme.color.blue[600].value};
    background: ${({ theme }) => theme.color.blue[600].value}0A;
  }
`;

export const SlotIcon = styled.span`
  font-size: 24px;
`;

export const SlotLabel = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.small.medium)}
  color: ${({ theme }) => theme.color.bluishGray[500].value};
`;

export const EquippedItemName = styled.span`
  ${({ theme }) => expandTypography(theme.typography.body.small.bold)}
  color: ${({ theme }) => theme.color.blue[600].value};
  text-align: center;
  word-break: keep-all;
`;
