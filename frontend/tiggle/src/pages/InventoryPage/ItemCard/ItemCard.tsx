import type {
  ItemCatalogRespDto,
  ItemSlotType,
  MemberItemRespDto,
} from "@/types/gamification";

import {
  ItemCardContainer,
  ItemImage,
  ItemName,
  ItemSlotLabel,
  LockOverlay,
  TierBadge,
} from "./ItemCardStyle";

const SLOT_LABELS: Record<string, string> = {
  HAT: "모자",
  OUTFIT: "의상",
  ACCESSORY: "악세서리",
  BACKGROUND: "배경",
  EFFECT: "이펙트",
  TITLE: "칭호",
};

const SLOT_ICONS: Record<string, string> = {
  HAT: "\uD83E\uDDE2",
  OUTFIT: "\uD83D\uDC55",
  ACCESSORY: "\uD83D\uDC8D",
  BACKGROUND: "\uD83C\uDF04",
  EFFECT: "\u2728",
  TITLE: "\uD83C\uDFC5",
};

interface ItemCardProps {
  item: MemberItemRespDto | ItemCatalogRespDto;
  locked?: boolean;
  onClick?: () => void;
}

const ItemCard = ({ item, locked = false, onClick }: ItemCardProps) => {
  const slot: ItemSlotType = item.slot;
  const tier = item.tier;

  return (
    <ItemCardContainer
      $tier={tier}
      $locked={locked}
      onClick={locked ? undefined : onClick}
    >
      {locked && <LockOverlay>{"\uD83D\uDD12"}</LockOverlay>}
      <ItemImage>{SLOT_ICONS[slot] ?? "\uD83D\uDCE6"}</ItemImage>
      <ItemName>{item.name}</ItemName>
      <ItemSlotLabel>{SLOT_LABELS[slot] ?? slot}</ItemSlotLabel>
      <TierBadge $tier={tier}>{tier}</TierBadge>
    </ItemCardContainer>
  );
};

export default ItemCard;
