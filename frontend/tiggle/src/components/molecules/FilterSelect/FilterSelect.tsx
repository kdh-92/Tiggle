import {
  Ref,
  forwardRef,
  useCallback,
  useEffect,
  useMemo,
  useState,
  useRef,
} from "react";
import { Check, ChevronDown } from "react-feather";

import cn from "classnames";

import { BottomSheet, PopOver } from "@/components/atoms";
import useOnClickOutside from "@/hooks/useOnClickOutside";
import { isDesktop } from "@/styles/util/screen";

import {
  FilterSelectButtonStyle,
  FilterSelectStyle,
  OptionCellStyle,
} from "./FilterSelectStyle";

interface OptionType<ValueType = string | number | null> {
  label: React.ReactNode;
  value: ValueType;
}

interface FilterSelectProps<ValueType> {
  options: Array<OptionType<ValueType>>;
  value?: Array<ValueType>;
  onChange?: (value: ValueType[]) => void;
  placeholder: string;
  disabled?: boolean;
}

const FilterSelect = forwardRef(
  <ValueType extends OptionType["value"]>(
    {
      placeholder,
      value,
      options,
      onChange,
      disabled = false,
    }: FilterSelectProps<ValueType>,
    ref: Ref<HTMLDivElement>,
  ) => {
    const desktop = isDesktop();
    const mobile = useMemo(() => !desktop, [desktop]);
    const wrapperRef = useRef<HTMLDivElement | null>(null);

    const [isOptionOpen, setIsOptionOpen] = useState(false);
    // TODO: Popover의 Inner value와 FormValue의 sync가 안맞는 문제 해결하기
    const [innerValue, setInnerValue] = useState<Array<ValueType>>(value ?? []);

    const toggleOptionOpen = useCallback(() => {
      setIsOptionOpen(!isOptionOpen);
    }, [isOptionOpen]);

    const closeOption = useCallback(() => {
      setIsOptionOpen(false);
    }, []);
    useOnClickOutside(wrapperRef, closeOption);

    const updateInnerValue = useCallback(
      (selected: ValueType) => {
        const newInnerValue = innerValue.includes(selected)
          ? innerValue.filter(v => v !== selected)
          : [...innerValue, selected];
        setInnerValue(newInnerValue);
        return newInnerValue;
      },
      [innerValue],
    );

    const selectPopoverOption = (selected: ValueType) => {
      const newInnerValue = updateInnerValue(selected);
      onChange?.(newInnerValue);
    };

    const selectBottomSheetOption = (selected: ValueType) => {
      updateInnerValue(selected);
    };

    const confirmBottomSheet = () => {
      onChange?.(innerValue);
      closeOption();
    };

    // todo: state 책임 명확하게 수정
    useEffect(() => {
      if (!isOptionOpen) {
        // initialized
        setInnerValue(value ?? []);
      }
    }, [isOptionOpen]);

    return (
      <FilterSelectStyle ref={ref}>
        <div className="wrapper" ref={wrapperRef}>
          <FilterSelectButtonStyle
            onClick={toggleOptionOpen}
            disabled={disabled}
            type="button"
          >
            {" "}
            <p className="placeholder">{placeholder}</p>
            <ChevronDown />
          </FilterSelectButtonStyle>

          {mobile && !disabled && (
            <BottomSheet
              isOpen={isOptionOpen}
              header={{ title: `${placeholder} 선택하기`, reset: true }}
              confirm={{ label: "확인", onClick: confirmBottomSheet }}
              onClose={closeOption}
            >
              {options.map(option => (
                <OptionCell
                  key={`option-${option.value}`}
                  option={option}
                  onClick={selectBottomSheetOption}
                  selected={innerValue.includes(option.value)}
                />
              ))}
            </BottomSheet>
          )}

          {desktop && !disabled && (
            <PopOver
              isOpen={isOptionOpen}
              header={{ title: `${placeholder} 선택하기`, reset: true }}
            >
              {options.map(option => (
                <OptionCell
                  key={`option-${option.value}`}
                  option={option}
                  onClick={selectPopoverOption}
                  selected={innerValue.includes(option.value)}
                />
              ))}
            </PopOver>
          )}
        </div>
      </FilterSelectStyle>
    );
  },
);

export default FilterSelect;

interface OptionCellProps<ValueType> {
  option: OptionType<ValueType>;
  onClick: (value: ValueType) => void;
  selected: boolean;
}

const OptionCell = <ValueType extends OptionType["value"]>({
  option: { label, value },
  onClick,
  selected,
}: OptionCellProps<ValueType>) => {
  return (
    <OptionCellStyle
      id={`option-${value}`}
      className={cn({ selected })}
      onClick={() => onClick(value)}
    >
      <p className="popover-option-label">{label}</p>
      {selected && <Check size={16} />}
    </OptionCellStyle>
  );
};
