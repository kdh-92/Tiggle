import { useCallback, useMemo, useRef, useState } from "react";
import { ChevronUp, Filter as FilterIcon, Plus } from "react-feather";
import { Controller, useFormContext } from "react-hook-form";

import cn from "classnames";

import FilterSelect from "@/components/molecules/FilterSelect/FilterSelect";

import {
  ETCFilterHeaderStyle,
  ETCFilterAccordionStyle,
  ETCFilterStyle,
} from "./ETCFilterStyle";
import ETCFilterTag from "../ETCFilterTag/ETCFilterTag";
import {
  useAllAssetsQuery,
  useAllCategoriesQuery,
  useAllTagsQuery,
} from "../query";
import { FilterInputs } from "../types";

interface ETCFilterProps {
  onChange?: (value: string) => void;
}

const ETCFilter = ({}: ETCFilterProps) => {
  const { control, watch } = useFormContext<FilterInputs>();
  const [isAccordionOpen, setIsAccordionOpen] = useState<boolean>(false);
  const accordionRef = useRef<HTMLDivElement | null>(null);

  const { data: assetsData } = useAllAssetsQuery();
  const assetOptions = useMemo(
    () => assetsData?.map(({ id, name }) => ({ label: name, value: id })) ?? [],
    [assetsData],
  );
  const { data: categoriesData } = useAllCategoriesQuery();
  const categoryOptions = useMemo(
    () =>
      categoriesData?.map(({ id, name }) => ({ label: name, value: id })) ?? [],
    [categoriesData],
  );
  const { data: tagsData } = useAllTagsQuery();
  const tagOptions = useMemo(
    () => tagsData?.map(({ id, name }) => ({ label: name, value: id })) ?? [],
    [tagsData],
  );

  const accordionHeight = useMemo(() => {
    const { current } = accordionRef;
    if (!current) return 0;
    return isAccordionOpen ? current.scrollHeight : 0;
  }, [isAccordionOpen]);

  const toggleSelectsOpen = useCallback(
    () => setIsAccordionOpen(!isAccordionOpen),
    [isAccordionOpen],
  );

  const watchSelected = watch(["assetIds", "categoryIds", "tagIds"]);

  const selectedETCTags = useMemo(() => {
    const [assetIds, categoryIds, tagIds] = watchSelected;

    const assetTags = assetIds
      ?.map(id => assetsData?.find(data => data.id === id))
      .map(({ id, name }) => ({
        label: `${name}`,
        value: id,
        keyName: "assetIds" as const,
      }));

    const categoryTags = categoryIds
      ?.map(id => categoriesData?.find(data => data.id === id))
      .map(({ id, name }) => ({
        label: `${name}`,
        value: id,
        keyName: "categoryIds" as const,
      }));

    const tagTags = tagIds
      ?.map(id => tagsData?.find(data => data.id === id))
      .map(({ id, name }) => ({
        label: `${name}`,
        value: id,
        keyName: "tagIds" as const,
      }));

    return [...(assetTags ?? []), ...(categoryTags ?? []), ...(tagTags ?? [])];
  }, [watchSelected]);

  return (
    <ETCFilterStyle className="filter-item">
      <ETCFilterHeaderStyle>
        <div className="title">
          <div className="title-text">
            <FilterIcon />
            <p>필터</p>
          </div>
          <button className="title-toggle" onClick={toggleSelectsOpen}>
            <Plus className={cn(!isAccordionOpen && "show")} />
            <ChevronUp className={cn(isAccordionOpen && "show")} />
          </button>
        </div>

        {selectedETCTags?.length > 0 && (
          <div className="selected-filter">
            {selectedETCTags.map(props => (
              <ETCFilterTag key={`filter-${props.label}`} {...props} />
            ))}
          </div>
        )}
      </ETCFilterHeaderStyle>

      <ETCFilterAccordionStyle
        ref={accordionRef}
        className={cn(isAccordionOpen ? "open" : "close")}
        _height={accordionHeight}
      >
        <div className="container">
          <Controller
            control={control}
            name="assetIds"
            render={({ field }) => (
              <FilterSelect
                placeholder="자산"
                options={assetOptions}
                {...field}
              />
            )}
          />
          <Controller
            control={control}
            name="categoryIds"
            render={({ field }) => (
              <FilterSelect
                placeholder="카테고리"
                options={categoryOptions}
                {...field}
              />
            )}
          />
          <Controller
            control={control}
            name="tagIds"
            render={({ field }) => (
              <FilterSelect
                placeholder="해시태그"
                options={tagOptions}
                {...field}
              />
            )}
          />
        </div>
      </ETCFilterAccordionStyle>
    </ETCFilterStyle>
  );
};

export default ETCFilter;
