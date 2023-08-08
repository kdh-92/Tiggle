import styled from "styled-components";

export const MenuButtonStyle = styled.button`
  width: 36px;
  height: 36px;
  background-color: ${({ theme: { color } }) => color.white.value};
  border: 1px solid ${({ theme: { color } }) => color.bluishGray[200].value};
  border-radius: 8px;

  display: flex;
  align-items: center;
  justify-content: center;
  transition:
    background-color 0.1s ease,
    color 0.1s ease;
  cursor: pointer;

  .menu-icon {
    width: 16px;
    height: 16px;
    color: ${({ theme: { color } }) => color.bluishGray[400].value};
  }

  &:active {
    background-color: ${({ theme: { color } }) => color.bluishGray[50].value};
  }
`;
