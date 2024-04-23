import { createGlobalStyle } from "styled-components";

import { reset } from "@/styles/config/_reset";

export const GlobalStyle = createGlobalStyle`
  ${reset}
  
  body {
    font-family: 'Pretendard Variable', Pretendard, system-ui, sans-serif;
    
    margin: 0;
    min-width: 375px;
    width: 100vw;
    height: 100vh;
    background-color: ${({ theme }) => theme.color.bluishGray[50].value};
  }
  
  * {
    box-sizing: border-box;
  }

  a {
    color: inherit;
    text-decoration: none;
  }
  
  br.break-m {
    ${({ theme }) => theme.mq.desktop} {
      display: none;
    }
  }
  br.break-d {
    display: none;
    ${({ theme }) => theme.mq.desktop} {
      display: inline-block;
    }
  }
`;
