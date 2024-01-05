export const formatNumber = (num: number) => {
  return Math.trunc(num)
    .toString()
    .replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};
