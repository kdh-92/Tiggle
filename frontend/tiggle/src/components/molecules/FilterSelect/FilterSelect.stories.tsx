import { useState } from "react";

import { StoryObj, Meta } from "@storybook/react";

import FilterSelect from "./FilterSelect";

export default {
  title: "molecules/FilterSelect",
  component: FilterSelect,
} as Meta<typeof FilterSelect>;

type Story = StoryObj<typeof FilterSelect>;

export const Default: Story = {
  args: {
    placeholder: "카테고리",
    options: [
      { label: "생활비", value: 1 },
      { label: "생활비", value: 2 },
      { label: "생활비", value: 3 },
    ],
    onChange: () => console.log("select"),
  },
  render: args => {
    const [value, setValue] = useState<Array<number>>([]);

    const handleOnChange = (newValue: Array<number>) => {
      setValue(newValue);
    };

    return (
      <>
        <FilterSelect {...args} value={value} onChange={handleOnChange} />
        <div>
          <p>selected</p>
          <ul>
            {value.map(v => (
              <li>{v}</li>
            ))}
          </ul>
        </div>
      </>
    );
  },
};
