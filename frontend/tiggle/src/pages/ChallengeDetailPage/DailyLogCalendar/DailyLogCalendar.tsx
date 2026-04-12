import dayjs from "dayjs";

import { DailyLogRespDto } from "@/types/gamification";

import { DailyLogCalendarStyle, DayCell } from "./DailyLogCalendarStyle";

interface DailyLogCalendarProps {
  logs: DailyLogRespDto[];
  startDate: string;
  endDate: string;
}

const WEEKDAY_LABELS = ["일", "월", "화", "수", "목", "금", "토"];

const formatAmount = (amount: number): string => {
  if (amount >= 10000) {
    return `${Math.round(amount / 10000)}만`;
  }
  if (amount >= 1000) {
    return `${(amount / 1000).toFixed(0)}천`;
  }
  return amount.toLocaleString();
};

const DailyLogCalendar = ({
  logs,
  startDate,
  endDate,
}: DailyLogCalendarProps) => {
  const start = dayjs(startDate);
  const end = dayjs(endDate);
  const today = dayjs();

  const logMap = new Map<string, DailyLogRespDto>();
  logs.forEach(log => {
    logMap.set(log.logDate, log);
  });

  // Build calendar cells
  const startDayOfWeek = start.day(); // 0=Sun
  const cells: Array<{
    date: dayjs.Dayjs | null;
    type: "no-spend" | "spent" | "future" | "empty";
    log?: DailyLogRespDto;
  }> = [];

  // Fill empty cells before start date
  for (let i = 0; i < startDayOfWeek; i++) {
    cells.push({ date: null, type: "empty" });
  }

  // Fill actual date cells
  let current = start;
  while (current.isBefore(end) || current.isSame(end, "day")) {
    const dateStr = current.format("YYYY-MM-DD");
    const log = logMap.get(dateStr);

    let type: "no-spend" | "spent" | "future";
    if (current.isAfter(today, "day")) {
      type = "future";
    } else if (log) {
      type = log.isNoSpend ? "no-spend" : "spent";
    } else {
      type = "future";
    }

    cells.push({ date: current, type, log });
    current = current.add(1, "day");
  }

  return (
    <DailyLogCalendarStyle>
      <div className="weekday-header">
        {WEEKDAY_LABELS.map(label => (
          <div key={label} className="weekday">
            {label}
          </div>
        ))}
      </div>

      <div className="calendar-grid">
        {cells.map((cell, index) => (
          <DayCell key={index} $type={cell.type}>
            {cell.date && (
              <>
                <span className="day-number">{cell.date.date()}</span>
                {cell.type === "spent" &&
                  cell.log &&
                  cell.log.outcomeAmount > 0 && (
                    <span className="day-amount">
                      {formatAmount(cell.log.outcomeAmount)}
                    </span>
                  )}
              </>
            )}
          </DayCell>
        ))}
      </div>
    </DailyLogCalendarStyle>
  );
};

export default DailyLogCalendar;
