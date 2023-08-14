import styled from "styled-components";

export const FeedsStyle = styled.div`
  padding: 0 32px;
  margin-top: 16px;

  ${({ theme }) => theme.mq.desktop} {
    display: flex;
    justify-content: center;
  }

  ${({ theme }) => theme.mq.mobile} {
    padding: 0 24px;
  }

  .feed-box-masonry {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    width: auto;
    gap: 24px;

    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
    }
  }

  .feeds {
    background-clip: padding-box;
    width: 340px;

    ${({ theme }) => theme.mq.mobile} {
      width: 327px;
    }
  }
`;
