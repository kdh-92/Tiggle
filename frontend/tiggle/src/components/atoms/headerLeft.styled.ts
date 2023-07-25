import styled from "styled-components";

export const StyledHeaderLeft = styled.div`
  display: flex;
  align-items: center;
  width: 50%;
  height: 60px;

  > ul {
    width: 70%;
    border-bottom: none;
    color: #667ba3;

    @media (max-width: 768px) {
      display: none;
    }
  }
`;
