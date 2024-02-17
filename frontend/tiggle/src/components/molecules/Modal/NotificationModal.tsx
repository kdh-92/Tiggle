import { Bell } from "react-feather";

import { NotificationModalStyle } from "@/components/molecules/Modal/NotificationModalStyle";

export default function NotificationModal() {
  return (
    <NotificationModalStyle>
      <div className="notification-background">
        <div className="notification-modal-wrap">
          <div className="notification-modal">
            <div className="notification-title">알림</div>
            <div className="notification-content-wrap">
              <div className="notification-content-box">
                <div className="notification-content">
                  <Bell />
                  <p>새로운 알림이 없습니다.</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </NotificationModalStyle>
  );
}
