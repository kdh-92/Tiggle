<<<<<<< HEAD
import { ChevronRight } from "react-feather";

import { useQuery } from "@tanstack/react-query";
import { Avatar } from "antd";

import { MemberApiControllerService } from "@/generated/services/MemberApiControllerService";
import { MypageStyle } from "@/styles/pages/MypageStyle";

const MyPage = () => {
  const { data, isError, isLoading } = useQuery({
    queryKey: ["me"],
    queryFn: () => MemberApiControllerService.getMe(5),
  });

  return (
    <>
      <MypageStyle>
        {!isLoading && !isError && (
          <>
            <div className="user-info">
              <div className="user">
                {data.profileUrl ? (
                  <img
                    className="user-profile"
                    alt="user-profile"
                    src={data.profileUrl}
                  />
                ) : (
                  <Avatar />
                )}
              </div>
              <div className="user-greeting">
                <p>
                  <span className="user-name">{data.nickname}</span>님,
                </p>
                <p>안녕하세요</p>
              </div>
              <button className="profile-modify-button">프로필 수정</button>
            </div>
            <div className="user-transaction">
              <p className="transaction-title">내 거래 기록</p>
              <div className="transaction-cells">
                <div className="transaction-cell">
                  <span>거래기록1</span>
                </div>
                <div className="transaction-cell">
                  <span>거래기록2</span>
                </div>
                <div className="transaction-cell">
                  <span>거래기록3</span>
                </div>
                <div className="transaction-cell">
                  <button>내 거래기록 더 보기</button>
                </div>
              </div>
            </div>
            <div className="user-setting">
              <p className="setting-title">설정</p>
              <div className="setting-cells">
                <div className="setting-cell">
                  <span>자산 항목 관리</span> <ChevronRight />
                </div>
                <div className="setting-cell">
                  <span>지출 카테고리 관리</span> <ChevronRight />
                </div>
                <div className="setting-cell">
                  <span>수입 카테고리 관리</span> <ChevronRight />
                </div>
              </div>
            </div>
            <button className="logout-button">로그아웃</button>
          </>
        )}
      </MypageStyle>
    </>
  );
=======
const MyPage = () => {
  return <div>not found</div>;
>>>>>>> 9506e4b (feat: my page router 생성, main header 연결)
};

export default MyPage;
