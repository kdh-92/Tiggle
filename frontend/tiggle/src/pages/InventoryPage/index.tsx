import { useCallback, useState } from "react";

import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

import { ItemApiControllerService } from "@/generated";
import { itemKeys } from "@/query/queryKeys";
import type {
  EquipmentRespDto,
  ItemCatalogRespDto,
  ItemSlotType,
  MemberItemRespDto,
} from "@/types/gamification";
import { ItemSlot } from "@/types/gamification";
import withAuth, { AuthProps } from "@/utils/withAuth";

import EquipmentSlots from "./EquipmentSlots/EquipmentSlots";
import {
  EmptyMessage,
  EquipModalActions,
  EquipModalButton,
  EquipModalContent,
  EquipModalItem,
  EquipModalItemList,
  EquipModalItemName,
  EquipModalOverlay,
  EquipModalTitle,
  InventoryPageContainer,
  ItemGrid,
  PageTitle,
  SectionDivider,
  TabButton,
  TabContainer,
} from "./InventoryPageStyle";
import ItemCard from "./ItemCard/ItemCard";

type Tab = "inventory" | "catalog";

const EQUIPMENT_KEY_MAP: Record<ItemSlotType, keyof EquipmentRespDto> = {
  [ItemSlot.HAT]: "hatItemId",
  [ItemSlot.OUTFIT]: "outfitItemId",
  [ItemSlot.ACCESSORY]: "accessoryItemId",
  [ItemSlot.BACKGROUND]: "backgroundItemId",
  [ItemSlot.EFFECT]: "effectItemId",
  [ItemSlot.TITLE]: "titleItemId",
};

interface InventoryPageProps extends AuthProps {}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const InventoryPage = (_props: InventoryPageProps) => {
  const queryClient = useQueryClient();
  const [activeTab, setActiveTab] = useState<Tab>("inventory");
  const [equipSlot, setEquipSlot] = useState<ItemSlotType | null>(null);
  const [selectedItemId, setSelectedItemId] = useState<number | null>(null);

  // -- Queries --
  const { data: inventoryRes } = useQuery({
    queryKey: itemKeys.inventory(),
    queryFn: () => ItemApiControllerService.getInventory(),
  });

  const { data: catalogRes } = useQuery({
    queryKey: itemKeys.catalog(),
    queryFn: () => ItemApiControllerService.getCatalog(),
  });

  const { data: equipmentRes } = useQuery({
    queryKey: itemKeys.equipment(),
    queryFn: () => ItemApiControllerService.getMyEquipment(),
  });

  const inventory: MemberItemRespDto[] = inventoryRes?.data ?? [];
  const catalog: ItemCatalogRespDto[] = catalogRes?.data ?? [];
  const equipment: EquipmentRespDto = equipmentRes?.data ?? {
    hatItemId: null,
    outfitItemId: null,
    accessoryItemId: null,
    backgroundItemId: null,
    effectItemId: null,
    titleItemId: null,
  };

  // -- Mutation --
  const equipMutation = useMutation({
    mutationFn: (body: { slot: string; itemId: number | null }) =>
      ItemApiControllerService.equipItem(body),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: itemKeys.equipment() });
      queryClient.invalidateQueries({ queryKey: itemKeys.inventory() });
      closeModal();
    },
  });

  // -- Equip modal logic --
  const openEquipModal = useCallback(
    (slot: ItemSlotType) => {
      const currentEquippedId = equipment[EQUIPMENT_KEY_MAP[slot]];
      setEquipSlot(slot);
      setSelectedItemId(currentEquippedId);
    },
    [equipment],
  );

  const closeModal = useCallback(() => {
    setEquipSlot(null);
    setSelectedItemId(null);
  }, []);

  const handleEquipConfirm = useCallback(() => {
    if (!equipSlot) return;
    equipMutation.mutate({ slot: equipSlot, itemId: selectedItemId });
  }, [equipSlot, selectedItemId, equipMutation]);

  const slotItems = equipSlot
    ? inventory.filter(i => i.slot === equipSlot)
    : [];

  return (
    <InventoryPageContainer>
      <PageTitle>아이템</PageTitle>

      {/* Equipment section */}
      <EquipmentSlots
        equipment={equipment}
        items={inventory}
        onSlotClick={openEquipModal}
      />

      <SectionDivider />

      {/* Tabs */}
      <TabContainer>
        <TabButton
          $active={activeTab === "inventory"}
          onClick={() => setActiveTab("inventory")}
          type="button"
        >
          보유 아이템
        </TabButton>
        <TabButton
          $active={activeTab === "catalog"}
          onClick={() => setActiveTab("catalog")}
          type="button"
        >
          아이템 도감
        </TabButton>
      </TabContainer>

      {/* Inventory tab */}
      {activeTab === "inventory" && (
        <>
          {inventory.length === 0 ? (
            <EmptyMessage>보유한 아이템이 없습니다.</EmptyMessage>
          ) : (
            <ItemGrid>
              {inventory.map(item => (
                <ItemCard
                  key={item.itemId}
                  item={item}
                  onClick={() => openEquipModal(item.slot)}
                />
              ))}
            </ItemGrid>
          )}
        </>
      )}

      {/* Catalog tab */}
      {activeTab === "catalog" && (
        <>
          {catalog.length === 0 ? (
            <EmptyMessage>아이템 도감이 비어있습니다.</EmptyMessage>
          ) : (
            <ItemGrid>
              {catalog.map(item => (
                <ItemCard key={item.id} item={item} locked={!item.unlocked} />
              ))}
            </ItemGrid>
          )}
        </>
      )}

      {/* Equip modal */}
      {equipSlot && (
        <EquipModalOverlay onClick={closeModal}>
          <EquipModalContent onClick={e => e.stopPropagation()}>
            <EquipModalTitle>
              {equipSlot === "HAT" && "모자"}
              {equipSlot === "OUTFIT" && "의상"}
              {equipSlot === "ACCESSORY" && "악세서리"}
              {equipSlot === "BACKGROUND" && "배경"}
              {equipSlot === "EFFECT" && "이펙트"}
              {equipSlot === "TITLE" && "칭호"} 장착
            </EquipModalTitle>

            <EquipModalItemList>
              {/* Unequip option */}
              <EquipModalItem
                $selected={selectedItemId === null}
                onClick={() => setSelectedItemId(null)}
                type="button"
              >
                <EquipModalItemName>장착 해제</EquipModalItemName>
              </EquipModalItem>

              {slotItems.map(item => (
                <EquipModalItem
                  key={item.itemId}
                  $selected={selectedItemId === item.itemId}
                  onClick={() => setSelectedItemId(item.itemId)}
                  type="button"
                >
                  <EquipModalItemName>{item.name}</EquipModalItemName>
                </EquipModalItem>
              ))}

              {slotItems.length === 0 && (
                <EmptyMessage>
                  해당 슬롯에 장착할 수 있는 아이템이 없습니다.
                </EmptyMessage>
              )}
            </EquipModalItemList>

            <EquipModalActions>
              <EquipModalButton onClick={closeModal} type="button">
                취소
              </EquipModalButton>
              <EquipModalButton
                $primary
                onClick={handleEquipConfirm}
                type="button"
              >
                확인
              </EquipModalButton>
            </EquipModalActions>
          </EquipModalContent>
        </EquipModalOverlay>
      )}
    </InventoryPageContainer>
  );
};

export default withAuth(InventoryPage);
