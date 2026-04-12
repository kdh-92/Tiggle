import { useNavigate } from "react-router-dom";

import styled from "styled-components";

const NotFoundPageStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 60vh;
  gap: 16px;
  padding: 24px;
  text-align: center;

  .error-code {
    font-size: 64px;
    font-weight: 700;
    color: ${({ theme }) => theme.color.gray[400]?.value ?? "#999"};
    margin: 0;
  }

  .error-message {
    font-size: 18px;
    color: ${({ theme }) => theme.color.gray[600]?.value ?? "#666"};
    margin: 0;
  }

  .back-button {
    margin-top: 16px;
    padding: 12px 24px;
    border: none;
    border-radius: 8px;
    background: ${({ theme }) => theme.color.peach?.[500]?.value ?? "#ff6b6b"};
    color: white;
    font-size: 16px;
    cursor: pointer;

    &:hover {
      opacity: 0.9;
    }
  }
`;

const NotFoundPage = () => {
  const navigate = useNavigate();

  return (
    <NotFoundPageStyle>
      <p className="error-code">404</p>
      <p className="error-message">페이지를 찾을 수 없습니다.</p>
      <button className="back-button" onClick={() => navigate("/")}>
        홈으로 돌아가기
      </button>
    </NotFoundPageStyle>
  );
};

export default NotFoundPage;
