// === Enums ===

export const CharacterStage = {
  EGG: "EGG",
  HATCHING: "HATCHING",
  ACTIVE: "ACTIVE",
} as const;
export type CharacterStageType =
  (typeof CharacterStage)[keyof typeof CharacterStage];

export const CharacterTier = {
  COMMON: "COMMON",
  RARE: "RARE",
  EPIC: "EPIC",
  LEGENDARY: "LEGENDARY",
  UNIQUE: "UNIQUE",
} as const;
export type CharacterTierType =
  (typeof CharacterTier)[keyof typeof CharacterTier];

export const CharacterPath = {
  GOLD: "GOLD",
  NATURE: "NATURE",
  OCEAN: "OCEAN",
  STAR: "STAR",
  DRAGON: "DRAGON",
} as const;
export type CharacterPathType =
  (typeof CharacterPath)[keyof typeof CharacterPath];

export const ColorRarity = {
  NORMAL: "NORMAL",
  SHINE: "SHINE",
  HOLOGRAPHIC: "HOLOGRAPHIC",
  LEGENDARY: "LEGENDARY",
} as const;
export type ColorRarityType = (typeof ColorRarity)[keyof typeof ColorRarity];

export const ItemSlot = {
  HAT: "HAT",
  OUTFIT: "OUTFIT",
  ACCESSORY: "ACCESSORY",
  BACKGROUND: "BACKGROUND",
  EFFECT: "EFFECT",
  TITLE: "TITLE",
} as const;
export type ItemSlotType = (typeof ItemSlot)[keyof typeof ItemSlot];

export const ItemTier = {
  COMMON: "COMMON",
  RARE: "RARE",
  EPIC: "EPIC",
  LEGENDARY: "LEGENDARY",
  UNIQUE: "UNIQUE",
} as const;
export type ItemTierType = (typeof ItemTier)[keyof typeof ItemTier];

export const ChallengeType = {
  NO_SPEND: "NO_SPEND",
  BUDGET_LIMIT: "BUDGET_LIMIT",
} as const;
export type ChallengeTypeValue =
  (typeof ChallengeType)[keyof typeof ChallengeType];

export const ChallengeStatus = {
  ACTIVE: "ACTIVE",
  COMPLETED: "COMPLETED",
  FAILED: "FAILED",
  CANCELLED: "CANCELLED",
} as const;
export type ChallengeStatusType =
  (typeof ChallengeStatus)[keyof typeof ChallengeStatus];

export const AchievementConditionType = {
  RECORD_COUNT: "RECORD_COUNT",
  STREAK: "STREAK",
  CHALLENGE_COMPLETE: "CHALLENGE_COMPLETE",
  CATEGORY_COUNT: "CATEGORY_COUNT",
  SPENDING_DECREASE: "SPENDING_DECREASE",
  NO_ANOMALY_WEEKS: "NO_ANOMALY_WEEKS",
  NO_SPEND_DAYS: "NO_SPEND_DAYS",
  COLOR_RARITY: "COLOR_RARITY",
  CHARACTER_TIER: "CHARACTER_TIER",
} as const;
export type AchievementConditionTypeValue =
  (typeof AchievementConditionType)[keyof typeof AchievementConditionType];

// === Statistics DTOs ===

export interface WeeklyStatRespDto {
  weekStartDate: string;
  weekEndDate: string;
  totalOutcome: number;
  totalIncome: number;
  totalRefund: number;
  transactionCount: number;
  avgDailyOutcome: number;
  topCategoryId: number | null;
  topCategoryName: string | null;
}

export interface WeeklyComparisonRespDto {
  currentWeek: WeeklyStatRespDto;
  previousWeek: WeeklyStatRespDto | null;
  changeRate: number | null;
  isImproved: boolean | null;
  isAnomaly: boolean;
  anomalyRatio: number | null;
}

export interface CategoryBreakdownDto {
  categoryId: number;
  categoryName: string;
  amount: number;
  ratio: number;
}

export interface MonthlySummaryRespDto {
  year: number;
  month: number;
  totalOutcome: number;
  totalIncome: number;
  totalRefund: number;
  transactionCount: number;
  categoryBreakdown: CategoryBreakdownDto[];
}

// === Character DTOs ===

export interface CharacterFormDto {
  name: string;
  nameEn: string;
  description: string | null;
  imageKey: string;
}

export interface ColorRespDto {
  hue: number;
  saturation: number;
  lightness: number;
  rarity: ColorRarityType;
  cssValue: string;
}

export interface EggStatusDto {
  phase: number;
  records: number;
  nextPhaseAt: number;
}

export interface CharacterDetailRespDto {
  stage: CharacterStageType;
  tier: CharacterTierType | null;
  path: CharacterPathType | null;
  level: number;
  experience: number;
  nextLevelExp: number | null;
  currentForm: CharacterFormDto | null;
  color: ColorRespDto;
  egg: EggStatusDto | null;
}

export interface CharacterCatalogDto {
  id: number;
  tier: CharacterTierType;
  path: CharacterPathType;
  level: number;
  name: string;
  nameEn: string;
  description: string | null;
  requiredExp: number;
  imageKey: string;
}

// === Item DTOs ===

export interface MemberItemRespDto {
  itemId: number;
  name: string;
  nameEn: string;
  slot: ItemSlotType;
  tier: ItemTierType;
  imageKey: string;
  acquiredAt: string;
}

export interface ItemCatalogRespDto {
  id: number;
  name: string;
  nameEn: string;
  description: string | null;
  slot: ItemSlotType;
  tier: ItemTierType;
  imageKey: string;
  requiredCharacterLevel: number;
  unlocked: boolean;
}

export interface EquipmentRespDto {
  hatItemId: number | null;
  outfitItemId: number | null;
  accessoryItemId: number | null;
  backgroundItemId: number | null;
  effectItemId: number | null;
  titleItemId: number | null;
}

export interface EquipItemReqDto {
  slot: ItemSlotType;
  itemId: number | null;
}

// === Achievement DTOs ===

export interface AchievementRespDto {
  id: number;
  code: string;
  name: string;
  description: string | null;
  conditionType: AchievementConditionTypeValue;
  conditionValue: number;
  achieved: boolean;
  achievedAt: string | null;
}

// === Challenge DTOs ===

export interface ChallengeRespDto {
  id: number;
  type: ChallengeTypeValue;
  status: ChallengeStatusType;
  startDate: string;
  endDate: string;
  targetDays: number;
  achievedDays: number;
  createdAt: string;
}

export interface DailyLogRespDto {
  logDate: string;
  isNoSpend: boolean;
  outcomeAmount: number;
}

export interface ChallengeDetailRespDto {
  challenge: ChallengeRespDto;
  dailyLogs: DailyLogRespDto[];
}

export interface ChallengeCreateReqDto {
  type: ChallengeTypeValue;
  targetDays: number;
}

export interface PageRespDto<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  empty: boolean;
}
