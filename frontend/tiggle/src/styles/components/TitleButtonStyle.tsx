import { Button } from "antd";
import styled from "styled-components";

export const TitleButtonStyle = styled(Button)`
  background-color: ${({ theme: { color } }) => color.black.value};
  color: ${({ theme: { color } }) => color.white.value};
  width: 148px;
  height: 48px;
  margin-top: 24px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  padding: 0 32px 0 32px;

  &:hover {
    border: none !important;
    color: ${({ theme: { color } }) => color.white.value} !important;
  }
`;
