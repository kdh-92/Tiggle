import { Bell } from "react-feather";
import { useDispatch, useSelector } from "react-redux";

import { NotificationModalStyle } from "@/components/molecules/Modal/NotificationModalStyle";
import { RootState } from "@/store";
import { toggleModal } from "@/store/notificationModal";

export default function NotificationModal() {
  const dispatch = useDispatch();
  const isModalOpen = useSelector(
    (state: RootState) => state.notificationModal.isOpen,
  );

  console.log(isModalOpen);

  return (
    <>
      {isModalOpen && (
        <NotificationModalStyle>
          <div
            className="notification-background"
            onClick={() => dispatch(toggleModal())}
          >
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
      )}
    </>
  );
}
