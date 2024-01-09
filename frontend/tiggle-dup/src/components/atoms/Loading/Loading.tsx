import { LoadingStyle } from "@/components/atoms/Loading/LoadingStyle";

const Loading = () => {
  return (
    <LoadingStyle>
      <div className="lds-roller">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </div>
    </LoadingStyle>
  );
};

export default Loading;
