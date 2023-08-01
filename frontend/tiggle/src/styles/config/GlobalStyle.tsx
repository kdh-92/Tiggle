import { createGlobalStyle, css } from "styled-components";
import { reset } from "@/styles/config/_reset";

export const GlobalStyle = createGlobalStyle`
  ${reset}
  
  body {
    font-family: 'Pretendard Variable', Pretendard, system-ui, sans-serif;
    
    margin: 0;
    width: 100vw;
    height: 100vh;
  }
`;
