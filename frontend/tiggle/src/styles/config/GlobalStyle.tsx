import { createGlobalStyle } from "styled-components";

import { reset } from "@/styles/config/_reset";

export const GlobalStyle = createGlobalStyle`
  ${reset}
  
  body {
    font-family: 'Pretendard Variable', Pretendard, system-ui, sans-serif;
    
    margin: 0;
    width: 100vw;
    height: 100vh;
  }
  
  * {
    box-sizing: border-box;
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
  
  .page-container {
    width: 100%;
    padding: 0 24px;
    
    ${({ theme }) => theme.mq.desktop} {
      width: 768px;
      margin: 0 auto;
      padding: 0 32px;
    }
  }
`;
