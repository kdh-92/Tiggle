import { ChevronRight } from "react-feather";

import { useQuery } from "@tanstack/react-query";
import { Avatar } from "antd";
import dayjs from "dayjs";

import { TransactionApiControllerService } from "@/generated";
import useLogin from "@/hooks/useLogin";
import { MypageStyle } from "@/styles/pages/MypageStyle";

import MyTransactionCell from "./MyTransactionCell";

const MyPage = () => {
  const { isLogin, profile, isProfileError, isProfileLoading, logOut } =
    useLogin();

  const {
    data,
    isError: isTxError,
    isLoading: isTxLoading,
  } = useQuery(
    ["myTransaction"],
    async () =>
      TransactionApiControllerService.getMemberCountOffsetTransaction(
        profile?.id,
        0,
      ),
    {
      enabled: isLogin,
      staleTime: 1000 * 60 * 10,
    },
  );

  const sortArray = data?.content.sort((a, b) =>
    dayjs(b.date).diff(dayjs(a.date)),
  );

  return (
    <>
      <MypageStyle>
        {!isProfileLoading && !isProfileError && (
          <div className="user-info">
            <div className="user">
              {profile.profileUrl ? (
                <img
                  className="user-profile"
                  alt="user-profile"
                  src={profile.profileUrl}
                />
              ) : (
                <Avatar />
              )}
            </div>
            <div className="user-greeting">
              <p>
                <span className="user-name">{profile.nickname}</span>님,
              </p>
              <p>안녕하세요</p>
            </div>
            <button className="profile-modify-button">프로필 수정</button>
          </div>
        )}
        {!isTxLoading && !isTxError && (
          <div className="user-transaction">
            <p className="transaction-title">내 거래 기록</p>
            <div className="transaction-cells">
              {sortArray.slice(0, 3).map(props => (
                <MyTransactionCell {...props} />
              ))}
              <div className="transaction-cell">
                <button>내 거래기록 더 보기</button>
              </div>
            </div>
          </div>
        )}
        <div className="user-setting">
          <p className="setting-title">설정</p>
          <div className="setting-cells">
            <button className="setting-cell">
              <span>자산 항목 관리</span> <ChevronRight />
            </button>
            <button className="setting-cell">
              <span>지출 카테고리 관리</span> <ChevronRight />
            </button>
            <button className="setting-cell">
              <span>수입 카테고리 관리</span> <ChevronRight />
            </button>
          </div>
        </div>
        <button onClick={() => logOut()} className="logout-button">
          로그아웃
        </button>
      </MypageStyle>
    </>
  );
};

export default MyPage;
