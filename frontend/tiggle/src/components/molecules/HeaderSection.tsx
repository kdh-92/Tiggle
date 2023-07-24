import HeaderRight from "../atoms/HeaderRight"
import HeaderLeft from "../atoms/HeaderLeft"

export default function HeaderSection () {
    return (
        <div className="header-wrap">
            <HeaderLeft />
            <HeaderRight />
        </div>
    );
};
