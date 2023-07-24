import { Menu } from "antd";

export default function HeaderLeft() {
  return (
    <div className="logo-wrap">
        <p>tiggle</p>
        <Menu
            style={{
                color: "#667BA3",
            }}
            mode="horizontal"
            items={["통계", "랭킹"].map((el, index) => ({
                key: String(index + 1),
                label: el,
            }))}
        />
    </div>
  );
};
