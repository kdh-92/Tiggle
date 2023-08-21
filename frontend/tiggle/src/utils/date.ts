import dayjs from "dayjs";

export const calculateDateTimeDiff = (
  value: string,
  template: string = "YYYY.MM.DD",
) => {
  const target = dayjs(value);
  const position = dayjs().isBefore(target) ? 1 : -1;
  const suffix = position < 0 ? "전" : "후";

  const secondDiff = dayjs().diff(target, "second");
  if (secondDiff < 60) {
    return `${secondDiff}초 ${suffix}`;
  }

  const minuteDiff = dayjs().diff(target, "minute");
  if (minuteDiff < 60) {
    return `${minuteDiff}분 ${suffix}`;
  }

  const hourDiff = dayjs().diff(target, "hour");
  if (hourDiff < 24) {
    return `${hourDiff}시간 ${suffix}`;
  }

  const dayDiff = dayjs().diff(target, "day");
  if (dayDiff < 7) {
    if (dayDiff === 1) {
      return `${position < 0 ? "어제" : "내일"}`;
    }
    return `${dayDiff}일 ${suffix}`;
  }

  return target.format(template);
};
