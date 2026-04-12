import { useMemo, useState } from "react";
import { ChevronRight } from "react-feather";

import { useQuery } from "@tanstack/react-query";

import { CharacterApiControllerService } from "@/generated/services/CharacterApiControllerService";
import { characterKeys } from "@/query/queryKeys";
import {
  type CharacterCatalogDto,
  type CharacterPathType,
  type CharacterTierType,
} from "@/types/gamification";
import withAuth from "@/utils/withAuth";

import {
  CatalogPageStyle,
  CatalogTitle,
  PathSection,
  PathHeader,
  PathLabel,
  PathCount,
  PathHeaderLeft,
  ChevronIcon,
  FormGrid,
  FormCard,
  FormImagePlaceholder,
  FormLevel,
  FormName,
  FormExp,
  TierDot,
  EmptyState,
  BackLink,
} from "./CharacterCatalogPageStyle";

const PATH_ORDER: CharacterPathType[] = [
  "GOLD",
  "NATURE",
  "OCEAN",
  "STAR",
  "DRAGON",
];

const PATH_LABELS: Record<CharacterPathType, string> = {
  GOLD: "재물의 길",
  NATURE: "자연의 길",
  OCEAN: "바다의 길",
  STAR: "별의 길",
  DRAGON: "용의 길",
};

const TIER_COLORS: Record<CharacterTierType, string> = {
  COMMON: "#999999",
  RARE: "#438bea",
  EPIC: "#9333ea",
  LEGENDARY: "#eab308",
  UNIQUE: "#ec4899",
};

const CharacterCatalogPage = () => {
  const { data, isLoading, isError } = useQuery(
    characterKeys.catalog(),
    () => CharacterApiControllerService.getCatalog(),
    { staleTime: 1000 * 60 * 10 },
  );

  const catalog = (data?.data as CharacterCatalogDto[] | undefined) ?? [];

  const grouped = useMemo(() => {
    const map = new Map<CharacterPathType, CharacterCatalogDto[]>();
    for (const path of PATH_ORDER) {
      map.set(path, []);
    }
    for (const item of catalog) {
      const list = map.get(item.path);
      if (list) {
        list.push(item);
      }
    }
    // Sort each group by level
    for (const [, items] of map) {
      items.sort((a, b) => a.level - b.level);
    }
    return map;
  }, [catalog]);

  const [expanded, setExpanded] = useState<Set<CharacterPathType>>(
    () => new Set(PATH_ORDER),
  );

  const togglePath = (path: CharacterPathType) => {
    setExpanded(prev => {
      const next = new Set(prev);
      if (next.has(path)) {
        next.delete(path);
      } else {
        next.add(path);
      }
      return next;
    });
  };

  if (isLoading) {
    return (
      <CatalogPageStyle>
        <EmptyState>불러오는 중...</EmptyState>
      </CatalogPageStyle>
    );
  }

  if (isError) {
    return (
      <CatalogPageStyle>
        <EmptyState>도감을 불러올 수 없습니다.</EmptyState>
      </CatalogPageStyle>
    );
  }

  return (
    <CatalogPageStyle>
      <BackLink href="/mypage/character">← 캐릭터로 돌아가기</BackLink>
      <CatalogTitle>캐릭터 도감</CatalogTitle>

      {PATH_ORDER.map(path => {
        const items = grouped.get(path) ?? [];
        const isExpanded = expanded.has(path);

        return (
          <PathSection key={path}>
            <PathHeader $expanded={isExpanded} onClick={() => togglePath(path)}>
              <PathHeaderLeft>
                <PathLabel>{PATH_LABELS[path]}</PathLabel>
                <PathCount>{items.length}종</PathCount>
              </PathHeaderLeft>
              <ChevronIcon $expanded={isExpanded}>
                <ChevronRight size={20} />
              </ChevronIcon>
            </PathHeader>

            {isExpanded && items.length > 0 && (
              <FormGrid>
                {items.map(form => {
                  const tierColor = TIER_COLORS[form.tier];
                  return (
                    <FormCard key={form.id} $tierColor={tierColor}>
                      <FormImagePlaceholder $tierColor={tierColor}>
                        <FormLevel $tierColor={tierColor}>
                          Lv.{form.level}
                        </FormLevel>
                      </FormImagePlaceholder>
                      <FormName>{form.name}</FormName>
                      <FormExp>
                        필요 경험치: {form.requiredExp.toLocaleString()}
                      </FormExp>
                      <TierDot $color={tierColor} />
                    </FormCard>
                  );
                })}
              </FormGrid>
            )}

            {isExpanded && items.length === 0 && (
              <FormGrid>
                <FormCard $tierColor="#999">
                  <EmptyState>아직 등록된 캐릭터가 없습니다.</EmptyState>
                </FormCard>
              </FormGrid>
            )}
          </PathSection>
        );
      })}
    </CatalogPageStyle>
  );
};

export default withAuth(CharacterCatalogPage);
