import { ChevronRight } from "react-feather";

import { useQuery } from "@tanstack/react-query";
import { Avatar } from "antd";
import styled from "styled-components";

import { MemberApiControllerService } from "@/generated/services/MemberApiControllerService";
import { expandTypography } from "@/styles/util/expandTypography";

export const MypageStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  .user-info {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 180px;
    padding: 60px 0;

    > .user {
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 8px;

      > .user-profile {
        border-radius: 50%;
        width: 80px;
        height: 80px;
      }

      > .ant-avatar {
        width: 80px;
        height: 80px;
        background-color: ${({ theme }) =>
          theme.color.bluishGray[300].value} !important;
      }
    }

    > .user-greeting {
      text-align: center;
      margin-bottom: 20px;
      ${({ theme }) => expandTypography(theme.typography.title.large.bold)}
      color: ${({ theme: { color } }) => color.bluishGray[800].value};

      & span {
        color: ${({ theme: { color } }) => color.blue[600].value};
      }
    }

    > .profile-modify-button {
      width: fit-content;
      padding: 8px 24px;
      border-radius: 8px;
      background: ${({ theme }) => theme.color.bluishGray[100].value};
      color: ${({ theme: { color } }) => color.bluishGray[600].value};
      ${({ theme }) => expandTypography(theme.typography.body.medium.bold)}
    }
  }

  .user-transaction {
    margin-bottom: 80px;

    .transaction-title {
      color: ${({ theme: { color } }) => color.bluishGray[600].value};
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
      margin-bottom: 12px;
    }

    .transaction-cells {
      width: 480px;
      background: ${({ theme }) => theme.color.white.value};
      border-radius: 16px;

      .transaction-cell {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 72px;
        padding: 24px 28px;
        color: ${({ theme: { color } }) => color.bluishGray[700].value};
        border-bottom: 1px solid
          ${({ theme: { color } }) => color.bluishGray[100].value};
        &:last-child {
          justify-content: center;
          align-items: center;
          border-bottom: none;
          > button {
            color: ${({ theme: { color } }) => color.blue[600].value};
            ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
          }
        }
      }
    }
  }

  .user-setting {
    margin-bottom: 80px;

    .setting-title {
      color: ${({ theme: { color } }) => color.bluishGray[600].value};
      ${({ theme }) => expandTypography(theme.typography.body.large.bold)}
      margin-bottom: 12px;
    }
    .setting-cells {
      width: 480px;
      background: ${({ theme }) => theme.color.white.value};
      border-radius: 16px;
      .setting-cell {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 72px;
        padding: 24px 28px;
        color: ${({ theme: { color } }) => color.bluishGray[700].value};
        border-bottom: 1px solid
          ${({ theme: { color } }) => color.bluishGray[100].value};
        &:last-child {
          border-bottom: none;
        }
        > svg {
          color: ${({ theme: { color } }) => color.bluishGray[400].value};
        }
      }
    }
  }

  .logout-button {
    width: 480px;
    padding: 24px 28px;
    border-radius: 12px;
    background: ${({ theme }) => theme.color.white.value};
    color: ${({ theme: { color } }) => color.blue[600].value};
    ${({ theme }) => expandTypography(theme.typography.body.large.medium)}
  }
`;

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
};

export default MyPage;
