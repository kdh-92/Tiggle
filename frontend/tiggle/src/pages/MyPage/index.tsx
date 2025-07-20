import { ChevronRight } from "react-feather";

import { useQuery } from "@tanstack/react-query";
import { Avatar } from "antd";
import dayjs from "dayjs";

import {
  TransactionApiControllerService,
  TransactionDtoWithCount,
} from "@/generated";
import useAuth from "@/hooks/useAuth";
import { MypageStyle } from "@/pages/MyPage/MypageStyle";
import { transactionKeys } from "@/query/queryKeys";
import { getProfileImageUrl } from "@/utils/imageUrl";
import withAuth, { AuthProps } from "@/utils/withAuth";

import MyTransactionCell from "./MyTransactionCell/MyTransactionCell";

interface MyPageProps extends AuthProps {}

const MyPage = ({ profile }: MyPageProps) => {
  const { isLogin, isLoginLoading, logOut } = useAuth();

  const {
    data,
    isLoading: isTxLoading,
    isError: isTxError,
  } = useQuery(
    transactionKeys.list({ id: profile.id }),
    async () =>
      TransactionApiControllerService.getMemberCountOffsetTransaction(
        profile.id,
        0,
      ),
    { staleTime: 1000 * 60 * 10, enabled: isLogin },
  );

  const sortArray = data?.data?.transactions?.sort(
    (a: TransactionDtoWithCount, b: TransactionDtoWithCount) =>
      dayjs(b.dto.date).diff(dayjs(a.dto.date)),
  );

  return (
    <MypageStyle>
      {!isLoginLoading && (
        <div className="user-info">
          <div className="user">
            {profile.profileUrl ? (
              <img
                className="user-profile"
                alt="user-profile"
                src={getProfileImageUrl(profile.profileUrl)}
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
          <a href="/mypage/profile">
            <button className="profile-modify-button">프로필 수정</button>
          </a>
        </div>
      )}
      {!isTxLoading && !isTxError && (
        <div className="user-transaction">
          <p className="transaction-title">내 거래 기록</p>
          <div className="transaction-cells">
            {sortArray
              ?.slice(0, 3)
              .map(props => (
                <MyTransactionCell
                  key={`tx-cell-${props.dto.id}`}
                  {...props.dto}
                />
              ))}
            <a href="/mypage/my-transactions">
              <div className="transaction-cell">
                <button>내 거래기록 더 보기</button>
              </div>
            </a>
          </div>
        </div>
      )}
      <div className="user-setting">
        <p className="setting-title">설정</p>
        <div className="setting-cells">
          {/*<a href="/mypage/setting/asset">*/}
          {/*  <button className="setting-cell">*/}
          {/*    <span>자산 관리</span> <ChevronRight />*/}
          {/*  </button>*/}
          {/*</a>*/}
          <a href="/mypage/setting/category">
            <button className="setting-cell">
              <span>카테고리 관리</span> <ChevronRight />
            </button>
          </a>
          {/*<a href="/mypage/setting/income-category">*/}
          {/*  <button className="setting-cell">*/}
          {/*    <span>수입 카테고리 관리</span> <ChevronRight />*/}
          {/*  </button>*/}
          {/*</a>*/}
        </div>
      </div>
      <button onClick={() => logOut()} className="logout-button">
        로그아웃
      </button>
    </MypageStyle>
  );
};

export default withAuth(MyPage);
