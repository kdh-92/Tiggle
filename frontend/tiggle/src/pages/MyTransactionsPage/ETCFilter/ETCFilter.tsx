import { useCallback, useMemo, useRef, useState } from "react";
import { ChevronUp, Filter as FilterIcon, Plus } from "react-feather";
import { Controller, useFormContext } from "react-hook-form";

import { useQuery } from "@tanstack/react-query";
import cn from "classnames";

import FilterSelect from "@/components/molecules/FilterSelect/FilterSelect";
import {
  CategoryApiControllerService,
  TagApiControllerService,
} from "@/generated";
import useAuth from "@/hooks/useAuth";
import { categoryKeys, tagKeys } from "@/query/queryKeys";

import {
  ETCFilterHeaderStyle,
  ETCFilterAccordionStyle,
  ETCFilterStyle,
} from "./ETCFilterStyle";
import ETCFilterTag from "../ETCFilterTag/ETCFilterTag";
import { FilterInputs } from "../types";

interface ETCFilterProps {
  onChange?: (value: string) => void;
}

const ETCFilter = ({}: ETCFilterProps) => {
  const { control, watch } = useFormContext<FilterInputs>();
  const { profile } = useAuth();
  const [isAccordionOpen, setIsAccordionOpen] = useState<boolean>(false);
  const accordionRef = useRef<HTMLDivElement | null>(null);

  const { data: categoriesData } = useQuery({
    queryKey: categoryKeys.lists(),
    queryFn: async () =>
      CategoryApiControllerService.getCategoryByMemberIdOrDefaults(),
    enabled: !!profile?.data?.id,
  });

  const categoryOptions = useMemo(
    () =>
      categoriesData?.data?.categories?.map(({ id, name }) => ({
        label: name!,
        value: id!,
      })) ?? [],
    [categoriesData],
  );

  const { data: tagsData } = useQuery({
    queryKey: tagKeys.lists(),
    queryFn: async () => TagApiControllerService.getAllDefaultTag(),
  });
  const tagOptions = useMemo(
    () =>
      tagsData?.data?.map(({ name }) => ({ label: name!, value: name! })) ?? [],
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

  const watchSelected = watch(["categoryIds", "tagNames"]);

  const selectedETCTags = useMemo(() => {
    const [categoryIds, tagNames] = watchSelected;

    const categoryTags = categoryIds
      ?.map(id =>
        categoriesData?.data?.categories?.find(data => data.id === id),
      )
      .map(category => ({
        label: `${category!.name}`,
        value: category!.id,
        keyName: "categoryIds" as const,
      }));

    const tagTags = tagNames
      ?.map(name => tagsData?.data?.find(data => data.name === name))
      .map(tag => ({
        label: `#${tag!.name}`,
        value: name,
        keyName: "tagNames" as const,
      }));

    return [...(categoryTags ?? []), ...(tagTags ?? [])];
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
              <ETCFilterTag
                key={`filter-${props.label}`}
                label={props.label}
                value={props.value!}
                keyName={props.keyName}
              />
            ))}
          </div>
        )}
      </ETCFilterHeaderStyle>

      <ETCFilterAccordionStyle
        ref={accordionRef}
        className={cn(isAccordionOpen ? "open" : "close")}
        $height={accordionHeight}
      >
        <div className="container">
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
            name="tagNames"
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
