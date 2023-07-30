import styled from "styled-components";
import { Header } from "antd/es/layout/layout";

export const HeaderStyle = styled(Header)`
  position: sticky;
  top: 0;
  z-index: 1;
  background: #fff;
  border-bottom: 1px solid #dfe4ec;
  padding-inline: 0;

  > div {
    display: flex;
    justify-content: center;
  }
`;

export const StyledHeaderLeft = styled.div`
  display: flex;
  align-items: center;
  width: 50%;
  height: 60px;

  .ant-menu {
    width: 70%;
    border-bottom: none;
    color: #667ba3;

    @media (max-width: 768px) {
      display: none;
    }
  }
`;

export const StyledHeaderRight = styled.div`
  display: flex;
  align-items: center;

  .bell {
    margin-right: 1rem;
  }
`;
