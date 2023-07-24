import { Avatar } from "antd";
import { Bell } from "react-feather";

export default function HeaderRight () {
  return (
    <div className="user-info">
      <Bell className="bell" />
      <Avatar />
    </div>
  );
};
