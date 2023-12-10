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
              <p>내 거래 기록</p>
            </div>
            <div className="user-setting">
              <p>설정</p>
            </div>
            <button className="logout-button">로그아웃</button>
          </>
        )}
      </MypageStyle>
    </>
  );
};

export default MyPage;
