import useCookie from "@/hooks/useCookie";

const NotFoundPage = () => {
  const { getCookie } = useCookie();

  const getVal = getCookie("Authorization");
  console.log(getVal);

  return <div>not found</div>;
};

export default NotFoundPage;
