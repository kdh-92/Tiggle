import { Dayjs } from "dayjs";

import { MemberDto } from "@/generated";

export type ProfileInputs = Partial<
  Omit<MemberDto, "id" | "birth" | "profileUrl"> & {
    birth: Dayjs;
    profileUrl: File[];
  }
>;
