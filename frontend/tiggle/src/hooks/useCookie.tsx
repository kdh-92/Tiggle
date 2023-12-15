import { Cookies } from "react-cookie";

export default function useCookie() {
  const cookies = new Cookies();

  const setCookie = (name: string, value: string, options: object) => {
    return cookies.set(name, value, { ...options });
  };

  const getCookie = (name: string) => {
    return cookies.get(name);
  };

  const removeCookie = (name: string) => {
    return cookies.remove(name, { path: "/" });
  };

  return { setCookie, getCookie, removeCookie };
}
