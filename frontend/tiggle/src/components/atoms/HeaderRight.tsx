import styled from "styled-components";
import { Avatar } from "antd";
import { Bell } from "react-feather";

const StyledHeaderRight = styled.div`
  display: flex;
  align-items: center;

  .bell {
    margin-right: 1rem;
  }
`;

export default function HeaderRight() {
  return (
    <StyledHeaderRight>
      <Bell className="bell" />
      <Avatar />
    </StyledHeaderRight>
  );
}
