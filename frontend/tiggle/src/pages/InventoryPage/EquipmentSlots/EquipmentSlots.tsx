import { ItemSlot } from "@/types/gamification";
import type {
  EquipmentRespDto,
  ItemSlotType,
  MemberItemRespDto,
} from "@/types/gamification";

import {
  EquipmentContainer,
  EquipmentTitle,
  EquippedItemName,
  SlotBox,
  SlotIcon,
  SlotLabel,
  SlotsGrid,
} from "./EquipmentSlotsStyle";

const SLOT_CONFIG: {
  slot: ItemSlotType;
  label: string;
  icon: string;
  equipKey: keyof EquipmentRespDto;
}[] = [
  {
    slot: ItemSlot.HAT,
    label: "모자",
    icon: "\uD83E\uDDE2",
    equipKey: "hatItemId",
  },
  {
    slot: ItemSlot.OUTFIT,
    label: "의상",
    icon: "\uD83D\uDC55",
    equipKey: "outfitItemId",
  },
  {
    slot: ItemSlot.ACCESSORY,
    label: "악세서리",
    icon: "\uD83D\uDC8D",
    equipKey: "accessoryItemId",
  },
  {
    slot: ItemSlot.BACKGROUND,
    label: "배경",
    icon: "\uD83C\uDF04",
    equipKey: "backgroundItemId",
  },
  {
    slot: ItemSlot.EFFECT,
    label: "이펙트",
    icon: "\u2728",
    equipKey: "effectItemId",
  },
  {
    slot: ItemSlot.TITLE,
    label: "칭호",
    icon: "\uD83C\uDFC5",
    equipKey: "titleItemId",
  },
];

interface EquipmentSlotsProps {
  equipment: EquipmentRespDto;
  items: MemberItemRespDto[];
  onSlotClick: (slot: ItemSlotType) => void;
}

const EquipmentSlots = ({
  equipment,
  items,
  onSlotClick,
}: EquipmentSlotsProps) => {
  const findItemName = (itemId: number | null): string | null => {
    if (itemId == null) return null;
    const found = items.find(i => i.itemId === itemId);
    return found?.name ?? null;
  };

  return (
    <EquipmentContainer>
      <EquipmentTitle>장착 장비</EquipmentTitle>
      <SlotsGrid>
        {SLOT_CONFIG.map(({ slot, label, icon, equipKey }) => {
          const equippedId = equipment[equipKey];
          const equippedName = findItemName(equippedId);
          const hasItem = equippedId != null;

          return (
            <SlotBox
              key={slot}
              $hasItem={hasItem}
              onClick={() => onSlotClick(slot)}
              type="button"
            >
              <SlotIcon>{icon}</SlotIcon>
              {equippedName ? (
                <EquippedItemName>{equippedName}</EquippedItemName>
              ) : (
                <SlotLabel>{label}</SlotLabel>
              )}
            </SlotBox>
          );
        })}
      </SlotsGrid>
    </EquipmentContainer>
  );
};

export default EquipmentSlots;
