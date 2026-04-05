import { Dayjs } from "dayjs";

import { MemberRespDto } from "@/generated";

export type ProfileInputs = Partial<
  Omit<MemberRespDto, "id" | "birth" | "profileUrl"> & {
    birth: Dayjs | null;
    profileUrl: File[];
  }
>;
