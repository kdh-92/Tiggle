import dayjs from "dayjs";
import duration, { Duration } from "dayjs/plugin/duration";

dayjs.extend(duration);

export default function timeDiff(time: string) {
  const createdAtTime = dayjs(time);
  const currentTime = dayjs(new Date());
  const formattedDifference: Duration = dayjs.duration(
    currentTime.diff(createdAtTime),
  );

  const yearDiff: number = parseInt(formattedDifference.format("Y"));
  const monthDiff: number = parseInt(formattedDifference.format("M"));
  const dateDiff: number = parseInt(formattedDifference.format("D"));
  const hourDiff: number = parseInt(formattedDifference.format("H"));
  const minuteDiff: number = parseInt(formattedDifference.format("m"));
  const secondDiff: number = parseInt(formattedDifference.format("s"));

  let diffTime = "";

  switch (true) {
    case yearDiff > 0:
      diffTime = `${yearDiff}년 전`;
      break;
    case monthDiff > 0:
      diffTime = `${monthDiff}달 전`;
      break;
    case dateDiff > 0:
      diffTime = `${dateDiff}일 전`;
      break;
    case hourDiff > 0:
      diffTime = `${hourDiff}시간 전`;
      break;
    case minuteDiff > 0:
      diffTime = `${minuteDiff}분 전`;
      break;
    case secondDiff > 0:
      diffTime = `${secondDiff}초 전`;
      break;
    default:
      diffTime;
  }

  return diffTime;
}
